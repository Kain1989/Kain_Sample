
package com.telenav.cloud.search.autonavi.entity.poi_generate;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Geocode {

    @Expose
    private String district;
    @Expose
    private String level;
    @Expose
    private String country;
    @Expose
    private String region;
    @Expose
    private String cityname;
    @Expose
    private String longitude;
    @Expose
    private String formattedaddress;
    @Expose
    private String latitude;

    /**
     * 
     * @return
     *     The district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 
     * @param district
     *     The district
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * 
     * @return
     *     The level
     */
    public String getLevel() {
        return level;
    }

    /**
     * 
     * @param level
     *     The level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The region
     */
    public String getRegion() {
        return region;
    }

    /**
     * 
     * @param region
     *     The region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * 
     * @return
     *     The cityname
     */
    public String getCityname() {
        return cityname;
    }

    /**
     * 
     * @param cityname
     *     The cityname
     */
    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    /**
     * 
     * @return
     *     The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 
     * @param longitude
     *     The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 
     * @return
     *     The formattedaddress
     */
    public String getFormattedaddress() {
        return formattedaddress;
    }

    /**
     * 
     * @param formattedaddress
     *     The formattedaddress
     */
    public void setFormattedaddress(String formattedaddress) {
        this.formattedaddress = formattedaddress;
    }

    /**
     * 
     * @return
     *     The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 
     * @param latitude
     *     The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
