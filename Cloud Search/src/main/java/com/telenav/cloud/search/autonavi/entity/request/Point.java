package com.telenav.cloud.search.autonavi.entity.request;

/**
 * Created by zfshi on 7/3/2015.
 */
public class Point {

    private Double longitude;

    private Double latitude;

    public Point(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
