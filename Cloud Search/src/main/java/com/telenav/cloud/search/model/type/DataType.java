package com.telenav.cloud.search.model.type;

/**
 * Created by zfshi on 6/30/2015.
 */
public enum DataType {

    POI("POI"),

    ROAD("ROAD"),

    ROADINTER("ROADINTER");

    private String code;

    DataType(String code) {
        this.code = code;
    }

    public DataType getQueryTypeByCode(String code) {
        if (POI.getCode().equals(code)) {
            return POI;
        }
        if (ROAD.getCode().equals(code)) {
            return ROAD;
        }
        if (ROADINTER.getCode().equals(code)) {
            return ROADINTER;
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
