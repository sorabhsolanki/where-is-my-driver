package com.locate.driver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {

    private final double latitude;
    private final double longitude;
    private final double radius;
    private final int limit;

    public CustomerDto(double latitude, double longitude, double radius, int limit) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.limit = limit;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRadius() {
        return radius;
    }

    public int getLimit() {
        return limit;
    }
}
