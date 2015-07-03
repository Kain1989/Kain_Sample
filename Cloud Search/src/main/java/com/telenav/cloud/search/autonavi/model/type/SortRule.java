package com.telenav.cloud.search.autonavi.model.type;

/**
 * Created by zfshi on 6/30/2015.
 */
public enum SortRule {

    MIXED(0),

    DISTANCE(1),

    NAME(2),

    LENGTH(3),

    SATURATION(4);

    private int code;

    SortRule(int code) {
        this.code = code;
    }

    public SortRule getSortRuleByCode(int code) {
        switch(code) {
            case 0:
                return MIXED;
            case 1:
                return DISTANCE;
            case 2:
                return NAME;
            case 3:
                return LENGTH;
            case 4:
                return SATURATION;
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
