
package com.telenav.cloud.search.entity.poi_generate;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class PoiList {

    @Expose
    private List<Poi> poi;

    /**
     * 
     * @return
     *     The poi
     */
    public List<Poi> getPoi() {
        return poi;
    }

    /**
     * 
     * @param poi
     *     The poi
     */
    public void setPoi(List<Poi> poi) {
        this.poi = poi;
    }

}
