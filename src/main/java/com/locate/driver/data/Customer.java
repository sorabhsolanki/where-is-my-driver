package com.locate.driver.data;

import com.locate.driver.dto.CustomerDto;

public class Customer {

    private final double latitude;
    private final double longitude;
    private final double radius;
    private final int limit;
    private final double[] point = new double[3];

    public Customer(double latitude, double longitude, double radius, int limit) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.limit = limit;
        calculatePoints();
    }

    public void calculatePoints() {
        point[0] = (double) (Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(this.longitude)));
        point[1] = (double) (Math.cos(Math.toRadians(this.latitude)) * Math.sin(Math.toRadians(this.longitude)));
        point[2] = (double) (Math.sin(Math.toRadians(this.latitude)));
    }

    public static Customer fromCustomerDto(final CustomerDto customerDto){
        return new Customer(customerDto.getLatitude(), customerDto.getLongitude(), customerDto.getRadius(),
                customerDto.getLimit());
    }

    public double[] getPoint() {
        return point;
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
