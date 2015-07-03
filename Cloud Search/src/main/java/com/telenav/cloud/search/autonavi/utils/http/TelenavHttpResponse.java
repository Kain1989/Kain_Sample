package com.telenav.cloud.search.autonavi.utils.http;

/**
 * Created by zfshi on 6/4/2015.
 */
public class TelenavHttpResponse {

    private int status;

    private String body;

    private String errorMsg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
