
package com.telenav.cloud.search.autonavi.entity.poi_generate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class PoiList {

    @SerializedName("poi")
    @Expose
    private List<Poi> pois;

    /**
     * 
     * @return
     *     The poi
     */
    public List<Poi> getPois() {
        return pois;
    }

    /**
     * 
     * @param poi
     *     The poi
     */
    public void setPois(List<Poi> pois) {
        this.pois = pois;
    }

}
