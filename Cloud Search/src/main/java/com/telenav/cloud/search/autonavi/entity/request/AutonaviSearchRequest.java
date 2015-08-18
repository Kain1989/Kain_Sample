package com.telenav.cloud.search.autonavi.entity.request;


import com.telenav.cloud.search.autonavi.model.type.DataType;
import com.telenav.cloud.search.autonavi.model.type.QueryType;
import com.telenav.cloud.search.autonavi.model.type.SortRule;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by zfshi on 6/30/2015.
 */
public class AutonaviSearchRequest {

    public static final String GEO_LINE_SEPERATOR = ";";

    private Long range;

    private Long pageSize;

    private Long pageNumber;

    private Point location;

    private Rectangle rectangle;

    private Boolean compress;

    private Boolean anonymous;

    private Boolean adcode;

    private Boolean mergeAddressPoi;

    private Boolean oneRow;

    private String address;

    private String output;

    private String language;

    private String keywords;

    private String words;

    private String id;

    private String geoobj;

    private String from;

    private String city;

    private String chanel;

    private String center;

    private SortRule sortRule;

    private QueryType queryType;

    private List<String> categoryList;

    private List<DataType> dataTypes;

    private List<Point> geoLine;

    public String generateCategoryQueryString() {
        return StringUtils.join(categoryList, "|");
    }

    public String generateGeoLineString() {
        StringBuilder lineStringBuilder = new StringBuilder();
        for (Point point : geoLine) {
            lineStringBuilder.append(point.getLongitude());
            lineStringBuilder.append(GEO_LINE_SEPERATOR);
            lineStringBuilder.append(point.getLatitude());
            lineStringBuilder.append(GEO_LINE_SEPERATOR);
        }
        String lineString = lineStringBuilder.toString();
        lineString = lineString.substring(0, lineString.length() - 1);
        return lineString;
    }

    public Long getRange() {
        return range;
    }

    public void setRange(Long range) {
        this.range = range;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Boolean getCompress() {
        return compress;
    }

    public void setCompress(Boolean compress) {
        this.compress = compress;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Boolean getAdcode() {
        return adcode;
    }

    public void setAdcode(Boolean adcode) {
        this.adcode = adcode;
    }

    public Boolean getMergeAddressPoi() {
        return mergeAddressPoi;
    }

    public void setMergeAddressPoi(Boolean mergeAddressPoi) {
        this.mergeAddressPoi = mergeAddressPoi;
    }

    public Boolean getOneRow() {
        return oneRow;
    }

    public void setOneRow(Boolean oneRow) {
        this.oneRow = oneRow;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGeoobj() {
        return geoobj;
    }

    public void setGeoobj(String geoobj) {
        this.geoobj = geoobj;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public SortRule getSortRule() {
        return sortRule;
    }

    public void setSortRule(SortRule sortRule) {
        this.sortRule = sortRule;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public List<DataType> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(List<DataType> dataTypes) {
        this.dataTypes = dataTypes;
    }

    public List<Point> getGeoLine() {
        return geoLine;
    }

    public void setGeoLine(List<Point> geoLine) {
        this.geoLine = geoLine;
    }

}
