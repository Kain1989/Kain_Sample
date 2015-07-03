
package com.telenav.cloud.search.autonavi.entity.poi_generate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Ats {

    @Expose
    private String result;
    @Expose
    private String code;
    @Expose
    private String message;
    @Expose
    private String timestamp;
    @Expose
    private String total;
    @Expose
    private Suggestion suggestion;
    @SerializedName("poi_list")
    @Expose
    private PoiList poiList;
    @SerializedName("road_list")
    @Expose
    private RoadList roadList;
    @SerializedName("roadinter_list")
    @Expose
    private RoadinterList roadinterList;

    /**
     * 
     * @return
     *     The result
     */
    public String getResult() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 
     * @return
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * @param timestamp
     *     The timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 
     * @return
     *     The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The suggestion
     */
    public Suggestion getSuggestion() {
        return suggestion;
    }

    /**
     * 
     * @param suggestion
     *     The suggestion
     */
    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    /**
     * 
     * @return
     *     The poiList
     */
    public PoiList getPoiList() {
        return poiList;
    }

    /**
     * 
     * @param poiList
     *     The poi_list
     */
    public void setPoiList(PoiList poiList) {
        this.poiList = poiList;
    }

    /**
     * 
     * @return
     *     The roadList
     */
    public RoadList getRoadList() {
        return roadList;
    }

    /**
     * 
     * @param roadList
     *     The road_list
     */
    public void setRoadList(RoadList roadList) {
        this.roadList = roadList;
    }

    /**
     * 
     * @return
     *     The roadinterList
     */
    public RoadinterList getRoadinterList() {
        return roadinterList;
    }

    /**
     * 
     * @param roadinterList
     *     The roadinter_list
     */
    public void setRoadinterList(RoadinterList roadinterList) {
        this.roadinterList = roadinterList;
    }

}
