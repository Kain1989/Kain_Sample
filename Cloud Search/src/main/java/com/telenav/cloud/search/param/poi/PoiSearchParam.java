package com.telenav.cloud.search.param.poi;


import com.telenav.cloud.search.model.type.DataType;
import com.telenav.cloud.search.model.type.QueryType;
import com.telenav.cloud.search.model.type.SortRule;

import java.util.List;

/**
 * Created by zfshi on 6/30/2015.
 */
public class PoiSearchParam extends SearchParam {

    private String id;

    private QueryType queryType;

    private List<DataType> dataTypes;

    private String center;

    private String geoobj;

    private String city;

    private String keywords;

    private String category;

    private double longitude;

    private double latitue;

    private SortRule sortRule;

    private long range;

    private boolean mergeAddrPoi;

    private long Page_size = 20;

    private long Page_number = 1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public List<DataType> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(List<DataType> dataTypes) {
        this.dataTypes = dataTypes;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getGeoobj() {
        return geoobj;
    }

    public void setGeoobj(String geoobj) {
        this.geoobj = geoobj;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitue() {
        return latitue;
    }

    public void setLatitue(double latitue) {
        this.latitue = latitue;
    }

    public SortRule getSortRule() {
        return sortRule;
    }

    public void setSortRule(SortRule sortRule) {
        this.sortRule = sortRule;
    }

    public long getRange() {
        return range;
    }

    public void setRange(long range) {
        this.range = range;
    }

    public boolean isMergeAddrPoi() {
        return mergeAddrPoi;
    }

    public void setMergeAddrPoi(boolean mergeAddrPoi) {
        this.mergeAddrPoi = mergeAddrPoi;
    }

    public long getPage_size() {
        return Page_size;
    }

    public void setPage_size(long page_size) {
        Page_size = page_size;
    }

    public long getPage_number() {
        return Page_number;
    }

    public void setPage_number(long page_number) {
        Page_number = page_number;
    }
}
