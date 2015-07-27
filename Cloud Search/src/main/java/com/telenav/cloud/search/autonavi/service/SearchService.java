package com.telenav.cloud.search.autonavi.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.exception.URLBuildException;
import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.entity.request.RequestKeyConstants;
import com.telenav.cloud.search.autonavi.entity.request.RequestValueConstants;
import com.telenav.cloud.search.autonavi.utils.coder.MD5Encoder;
import com.telenav.cloud.search.autonavi.utils.http.TelenavHttpClient;
import com.telenav.cloud.search.autonavi.utils.http.TelenavHttpResponse;
import com.telenav.cloud.search.autonavi.utils.json.JsonUtils;
import com.telenav.cloud.search.autonavi.utils.uri.TelenavUriBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.LinkedHashMap;
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
        this.httpClient = TelenavHttpClient.getInstance();
    }

    protected abstract Map<String, Object> generateQueryParameters(AutonaviSearchRequest request) throws AuthenticationBuildException;

    protected Map<String, Object> generateCommonParameters(AutonaviSearchRequest request) throws AuthenticationBuildException {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put(RequestKeyConstants.FROM, request.getFrom());
        params.put(RequestKeyConstants.OUTPUT, RequestValueConstants.JSON);
        params.put(RequestKeyConstants.CHANNEL, RequestValueConstants.TELENAV);
        if (request.getDataTypes() != null && request.getDataTypes().size() > 0) {
            params.put(RequestKeyConstants.DATA_TYPE, StringUtils.join(request.getDataTypes().toArray(), "+"));
        }
        if (request.getPageSize() != null) {
            params.put(RequestKeyConstants.PAGE_SIZE, request.getPageSize());
        }
        if (request.getPageNumber() != null) {
            params.put(RequestKeyConstants.PAGE_NUM, request.getPageNumber());
        }
        if (StringUtils.isNotBlank(request.getLanguage())) {
            params.put(RequestKeyConstants.LANGUAGE, request.getLanguage());
        }
        params.put(RequestKeyConstants.SIGN, this.getSignatureString(request));
        return params;
    }

    protected abstract String getAuthenticationString(AutonaviSearchRequest request) throws AuthenticationBuildException;

    public T search(AutonaviSearchRequest request) throws AuthenticationBuildException, URLBuildException {
        T result = null;
        TelenavHttpResponse response = null;
        Map<String, Object> params = this.generateQueryParameters(request);
        if (params == null) {
            throw new URLBuildException("Parameters are null, could not execute request");
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

    protected String getSignatureString(AutonaviSearchRequest request) throws AuthenticationBuildException {
        String str = getAuthenticationString(request);
        return MD5Encoder.getMD5(str.getBytes());
    }

    public void setHttpClient(TelenavHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }
}
