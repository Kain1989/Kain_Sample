package com.telenav.cloud.search.entity.poi;

import com.telenav.cloud.search.entity.keyword.Poi;

import java.util.List;

public class Ats {

    private String result;

    private String code;

    private String message;

    private String timestamp;

    private String total;

    private Suggestion suggestion;

    private List<Poi> poi_list;

    private List<Tip> tip_list;

    private List<Road> road_list;

    private List<RoadInter> roadinter_list;

    private String version;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public List<Poi> getPoi_list() {
        return poi_list;
    }

    public void setPoi_list(List<Poi> poi_list) {
        this.poi_list = poi_list;
    }

    public List<Tip> getTip_list() {
        return tip_list;
    }

    public void setTip_list(List<Tip> tip_list) {
        this.tip_list = tip_list;
    }

    public List<Road> getRoad_list() {
        return road_list;
    }

    public void setRoad_list(List<Road> road_list) {
        this.road_list = road_list;
    }

    public List<RoadInter> getRoadinter_list() {
        return roadinter_list;
    }

    public void setRoadinter_list(List<RoadInter> roadinter_list) {
        this.roadinter_list = roadinter_list;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
