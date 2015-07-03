package com.telenav.cloud.search.autonavi.model.type;

/**
 * Created by zfshi on 6/30/2015.
 */
public enum QueryType {

    ID("IDQ"),

    KEYWORDS("TQUERY"),

    LATLON("RQBXY"),

    RECTANGLE("SPQ"),

    CENTER("RQBN");

    private String code;

    QueryType(String code) {
        this.code = code;
    }

    public QueryType getQueryTypeByCode(String code) {
        if (ID.getCode().equals(code)) {
            return ID;
        }
        if (KEYWORDS.getCode().equals(code)) {
            return KEYWORDS;
        }
        if (LATLON.getCode().equals(code)) {
            return LATLON;
        }
        if (RECTANGLE.getCode().equals(code)) {
            return RECTANGLE;
        }
        if (CENTER.getCode().equals(code)) {
            return CENTER;
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
