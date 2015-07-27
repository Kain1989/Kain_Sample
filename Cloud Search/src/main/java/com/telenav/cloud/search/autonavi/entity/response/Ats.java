
package com.telenav.cloud.search.autonavi.entity.response;

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
    private String adcode;

    @Expose
    private String version;

    @Expose
    private String message;

    @Expose
    private String timestamp;

    @Expose
    private String province;

    @Expose
    private String city;

    @Expose
    private String citycode;

    @Expose
    private String districtadcode;

    @Expose
    private String district;

    @Expose
    private String cityadcode;

    @Expose
    private String provinceadcode;

    @Expose
    private String pos;

    @Expose
    private String country;

    @Expose
    private String tel;

    @Expose
    private String total;

    @Expose
    private Suggestion suggestion;

    @SerializedName("tip_list")
    @Expose
    private TipList tipList;

    @SerializedName("poi_list")
    @Expose
    private PoiList poiList;

    @SerializedName("road_list")
    @Expose
    private RoadList roadList;

    @SerializedName("roadinter_list")
    @Expose
    private RoadinterList roadinterList;

    @SerializedName("cross_list")
    @Expose
    private CrossList crossList;

    @Expose
    private Geocode geocode;

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

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getDistrictadcode() {
        return districtadcode;
    }

    public void setDistrictadcode(String districtadcode) {
        this.districtadcode = districtadcode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCityadcode() {
        return cityadcode;
    }

    public void setCityadcode(String cityadcode) {
        this.cityadcode = cityadcode;
    }

    public String getProvinceadcode() {
        return provinceadcode;
    }

    public void setProvinceadcode(String provinceadcode) {
        this.provinceadcode = provinceadcode;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public TipList getTipList() {
        return tipList;
    }

    public void setTipList(TipList tipList) {
        this.tipList = tipList;
    }

    public PoiList getPoiList() {
        return poiList;
    }

    public void setPoiList(PoiList poiList) {
        this.poiList = poiList;
    }

    public RoadList getRoadList() {
        return roadList;
    }

    public void setRoadList(RoadList roadList) {
        this.roadList = roadList;
    }

    public RoadinterList getRoadinterList() {
        return roadinterList;
    }

    public void setRoadinterList(RoadinterList roadinterList) {
        this.roadinterList = roadinterList;
    }

    public CrossList getCrossList() {
        return crossList;
    }

    public void setCrossList(CrossList crossList) {
        this.crossList = crossList;
    }

    public Geocode getGeocode() {
        return geocode;
    }

    public void setGeocode(Geocode geocode) {
        this.geocode = geocode;
    }
}
