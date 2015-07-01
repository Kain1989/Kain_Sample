package com.telenav.cloud.search.entity;

import java.util.Arrays;

/**
 * Created by zfshi on 6/12/2015.
 */
public class TipList {

    private String[] tip;

    @Override
    public String toString() {
        return "TipList{" +
                "tip=" + Arrays.toString(tip) +
                '}';
    }

    public String[] getTip() {
        return tip;
    }

    public void setTip(String[] tip) {
        this.tip = tip;
    }
}
