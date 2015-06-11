package com.telenav.cloud.search.entity.poi;

import java.util.List;

/**
 * Created by zfshi on 6/11/2015.
 */
public class Suggestion {

    private List<Keyword> keywords;

    private Regions regions;

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public Regions getRegions() {
        return regions;
    }

    public void setRegions(Regions regions) {
        this.regions = regions;
    }
}
