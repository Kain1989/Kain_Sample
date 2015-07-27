package com.telenav.cloud.search.autonavi.entity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

/**
 * Created by zfshi on 7/7/2015.
 */
@Generated("org.jsonschema2pojo")
public class TipList {

    @SerializedName("tip")
    @Expose
    private List<String> tips;

    public List<String> getTips() {
        return tips;
    }

    public void setTips(List<String> tips) {
        this.tips = tips;
    }
}
