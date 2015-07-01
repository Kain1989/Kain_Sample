
package com.telenav.cloud.search.entity.poi_generate;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class RoadList {

    @Expose
    private Road road;

    /**
     * 
     * @return
     *     The road
     */
    public Road getRoad() {
        return road;
    }

    /**
     * 
     * @param road
     *     The road
     */
    public void setRoad(Road road) {
        this.road = road;
    }

}
