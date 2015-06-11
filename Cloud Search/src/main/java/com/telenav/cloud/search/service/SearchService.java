package com.telenav.cloud.search.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.telenav.cloud.search.utils.coder.MD5Encoder;
import com.telenav.cloud.search.utils.http.TelenavHttpClient;
import com.telenav.cloud.search.utils.http.TelenavHttpResponse;
import com.telenav.cloud.search.utils.json.JsonUtils;
import com.telenav.cloud.search.utils.uri.TelenavUriBuilder;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.Map;

/**
 * Created by zfshi on 6/11/2015.
 */
public abstract class SearchService<T> {

    private static Logger logger = Logger.getLogger(SearchService.class);

    private Class<T> entityClass;

    protected TelenavHttpClient httpClient;

    protected String urlPrefix;

    protected SearchService() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected abstract Map<String, Object> generateQueryParameters();

    protected abstract String getAuthenticationString();

    public T search() {
        T result = null;
        TelenavHttpResponse response = null;
        Map<String, Object> params = this.generateQueryParameters();
        if (params == null) {
            logger.error("Parameters are null, could not execute request");
            return null;
        }
        URI uri = TelenavUriBuilder.build(this.urlPrefix, params);
        if (uri == null) {
            logger.error("Got null uri , uri prefix:" + this.urlPrefix + ", params:" + params);
            return null;
        }
        try {
            response = httpClient.doGet(uri.toString());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (response == null) {
            logger.error("Get null response by the request:" + uri.toString());
            return null;
        }
        if (response.getStatus() == HttpStatus.SC_OK) {
            logger.info("API call success : " + response.getBody());
            if (!isSuccess(response)) {
                logger.error("Do not find result");
                return null;
            }
            result = JsonUtils.fromJson(response.getBody(), entityClass);
            return result;
        } else {
            return null;
        }
    }

    private boolean isSuccess(TelenavHttpResponse response) {
        JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();
        JsonObject ats = jsonObject.getAsJsonObject("ats");
        JsonElement resultCode = ats.get("result");
        return resultCode.getAsBoolean();
    }

    protected String getSignatureString() {
        String str = getAuthenticationString();
        return MD5Encoder.getMD5(str.getBytes());
    }

    public void setHttpClient(TelenavHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }
}
