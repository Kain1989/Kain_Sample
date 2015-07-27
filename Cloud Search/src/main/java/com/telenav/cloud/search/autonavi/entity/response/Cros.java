
package com.telenav.cloud.search.autonavi.entity.response;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Cros {

    @Expose
    private String distance;
    @Expose
    private String direction;
    @Expose
    private String name;
    @Expose
    private String weight;
    @Expose
    private String level;
    @Expose
    private String longitude;
    @Expose
    private String crossid;
    @Expose
    private String width;
    @Expose
    private String latitude;

    /**
     * 
     * @return
     *     The distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * 
     * @param distance
     *     The distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * 
     * @return
     *     The direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 
     * @param direction
     *     The direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 
     * @param weight
     *     The weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
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
     *     The crossid
     */
    public String getCrossid() {
        return crossid;
    }

    /**
     * 
     * @param crossid
     *     The crossid
     */
    public void setCrossid(String crossid) {
        this.crossid = crossid;
    }

    /**
     * 
     * @return
     *     The width
     */
    public String getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(String width) {
        this.width = width;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(distance).append(direction).append(name).append(weight).append(level).append(longitude).append(crossid).append(width).append(latitude).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Cros) == false) {
            return false;
        }
        Cros rhs = ((Cros) other);
        return new EqualsBuilder().append(distance, rhs.distance).append(direction, rhs.direction).append(name, rhs.name).append(weight, rhs.weight).append(level, rhs.level).append(longitude, rhs.longitude).append(crossid, rhs.crossid).append(width, rhs.width).append(latitude, rhs.latitude).isEquals();
    }

}
