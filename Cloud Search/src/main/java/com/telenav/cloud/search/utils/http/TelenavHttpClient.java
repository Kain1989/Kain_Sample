package com.telenav.cloud.search.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by zfshi on 6/4/2015.
 */
public class TelenavHttpClient {

    private static Logger logger = Logger.getLogger(TelenavHttpClient.class);

    private PoolingHttpClientConnectionManager connectionManager;

    private TelenavHttpRetryHandler retryHandler;

    private RequestConfig requestConfig;

    private static TelenavHttpClient instance;

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public void testRun() throws IOException {
        this.doGet(url);
    }

    public static TelenavHttpClient getInstance() {
        Object obj = new Object();
        synchronized (obj) {
            if (instance == null) {
                instance = new TelenavHttpClient();
            }
        }
        return instance;
    }

    private TelenavHttpClient() {
        this.initHttpClient();
    }

    private void initHttpClient() {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(TelenavHttpParameters.MAX_CONNECTION_COUNT);
        connectionManager.setDefaultMaxPerRoute(TelenavHttpParameters.INITIAL_CONNECTION_COUNT);
        retryHandler = new TelenavHttpRetryHandler(TelenavHttpParameters.MAX_RETRY_COUNT);
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(TelenavHttpParameters.SOCKET_TIMEOUT)
                .setConnectTimeout(TelenavHttpParameters.CONNECT_TIMEOUT)
                .build();
    }

    public TelenavHttpResponse doGet(String url) throws IOException {
        TelenavHttpResponse telenavResponse = new TelenavHttpResponse();
        CloseableHttpClient httpClient = HttpClients.custom().
                setConnectionManager(connectionManager).
                setRetryHandler(retryHandler).
                setDefaultRequestConfig(requestConfig).build();
        HttpGet httpget = new HttpGet(url);
        logger.info("Url before send out :" + url);
        CloseableHttpResponse response = httpClient.execute(httpget);
        telenavResponse.setStatus(response.getStatusLine().getStatusCode());
        logger.info("Http Response Code is : " + telenavResponse.getStatus());
        try {
            String body = generateResponseBody(response);
            if (HttpStatus.SC_OK == telenavResponse.getStatus()) {
                telenavResponse.setBody(body);
            } else {
                telenavResponse.setErrorMsg(body);
                logger.warn("Http request failed, request URL : " + url);
                logger.warn(telenavResponse.getBody());
            }
        } finally {
            response.close();
//            httpClient.close();
        }
        return telenavResponse;
    }

    private String generateResponseBody(CloseableHttpResponse response) throws IOException {
        HttpEntity entity1 = response.getEntity();
        String body = "";
        if (entity1 != null) {
            body = EntityUtils.toString(entity1);
            logger.info("Response Body is : " + body);
        }
        EntityUtils.consume(entity1);
        return body;
    }
}