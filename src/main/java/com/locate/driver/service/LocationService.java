package com.locate.driver.service;

import com.locate.driver.data.DataHolder;
import com.locate.driver.dto.CustomerDto;
import com.locate.driver.dto.DriverDto;
import com.locate.driver.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocationService {

    private static final Logger LOG = LoggerFactory.getLogger(LocationService.class);

    @Autowired
    DataHolder dataHolder;

    public List<DriverDto> getDrivers(double latitude, double longitude, double radius, int limit) throws BadRequestException {

        if(Math.abs(latitude) > 90){
            LOG.warn("Latitude should be between +/- 90");
            throw new BadRequestException("Latitude should be between +/- 90");
        }

        CustomerDto customerDto = new CustomerDto(latitude, longitude, radius, limit);
        return dataHolder.getNearbyDrivers(customerDto);
    }

    public void addOrUpdateDriver(DriverDto driverDto) throws BadRequestException{
        if(Math.abs(driverDto.getLatitude()) > 90){
            LOG.warn("Latitude should be between +/- 90");
            throw new BadRequestException("Latitude should be between +/- 90");
        }
        dataHolder.put(driverDto);
    }
}
