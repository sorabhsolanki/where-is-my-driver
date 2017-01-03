package com.locate.driver.data;

import com.locate.driver.dto.DriverDto;

public class Driver {

    private final int id;
    private double latitude;
    private double longitude;
    private double accuracy;
    private final double[] point = new double[3];

    private Driver(int id, double latitude, double longitude, double accuracy) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
        calculatePoints();
    }

    public static Driver fromDriverDto(final DriverDto driverDto){
        return new Driver(driverDto.getId(), driverDto.getLatitude(), driverDto.getLongitude(),
                driverDto.getAccuracy());
    }

    public static DriverDto toDriverDto(final Driver driver){
        return new DriverDto(driver.id, driver.latitude, driver.longitude,
                driver.accuracy);
    }

    public void calculatePoints() {
        point[0] = (double) (Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(this.longitude)));
        point[1] = (double) (Math.cos(Math.toRadians(this.latitude)) * Math.sin(Math.toRadians(this.longitude)));
        point[2] = (double) (Math.sin(Math.toRadians(this.latitude)));
    }

    public double[] getPoint() {
        return point;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
