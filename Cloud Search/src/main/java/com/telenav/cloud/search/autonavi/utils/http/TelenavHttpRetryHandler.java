package com.telenav.cloud.search.autonavi.utils.http;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * Created by zfshi on 6/4/2015.
 */
public class TelenavHttpRetryHandler implements HttpRequestRetryHandler {

    private static Logger logger = Logger.getLogger(TelenavHttpRetryHandler.class);

    private int maxRetryCount;

    public TelenavHttpRetryHandler(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext httpContext) {
        if (executionCount >= maxRetryCount) {
            // Do not retry if over max retry count
            return false;
        }
        if (exception instanceof InterruptedIOException) {
            // Timeout
            logger.info("Meet timeout, will retry " + executionCount);
            return true;
        }
        if (exception instanceof UnknownHostException) {
            // Unknown host
            return false;
        }
        if (exception instanceof ConnectTimeoutException) {
            // Connection refused
            return true;
        }
        if (exception instanceof SSLException) {
            // SSL handshake exception
            return false;
        }
        HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
        HttpRequest request = clientContext.getRequest();
        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
        if (idempotent) {
            // Retry if the request is considered idempotent
            return true;
        }
        return false;
    }
}
