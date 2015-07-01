
package com.telenav.cloud.search.entity.poi_generate;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Suggestion {

    @Expose
    private Keywords keywords;
    @Expose
    private Regions regions;

    /**
     * 
     * @return
     *     The keywords
     */
    public Keywords getKeywords() {
        return keywords;
    }

    /**
     * 
     * @param keywords
     *     The keywords
     */
    public void setKeywords(Keywords keywords) {
        this.keywords = keywords;
    }

    /**
     * 
     * @return
     *     The regions
     */
    public Regions getRegions() {
        return regions;
    }

    /**
     * 
     * @param regions
     *     The regions
     */
    public void setRegions(Regions regions) {
        this.regions = regions;
    }

}
