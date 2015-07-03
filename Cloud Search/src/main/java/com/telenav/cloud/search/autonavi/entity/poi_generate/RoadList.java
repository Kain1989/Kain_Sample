
package com.telenav.cloud.search.autonavi.entity.poi_generate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class RoadList {

    @SerializedName("road")
    @Expose
    private List<Road> roads;

    /**
     * 
     * @return
     *     The road
     */
    public List<Road> getRoads() {
        return roads;
    }

    /**
     * 
     * @param road
     *     The road
     */
    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

}
