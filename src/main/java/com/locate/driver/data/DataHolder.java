package com.locate.driver.data;

import com.locate.driver.dto.CustomerDto;
import com.locate.driver.dto.DriverDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
   Main class for holding all the information of driver locations.

 * Idea is to maintain two DS, one hashmap which will map drivers id to the whole driver object.
 * Second is the LocationGraph which is actually a K-dimensional tree to search efficiently.

*/
@Component
public class DataHolder {

    private static final Logger LOG = LoggerFactory.getLogger(DataHolder.class);

    private final ConcurrentMap<Integer, Driver> driverMap;
    private final LocationGraph locationGraph;
    private final List<Driver> drivers;

    public DataHolder(){
        driverMap = new ConcurrentHashMap<>();
        drivers = new ArrayList<>();
        locationGraph = new LocationGraph(drivers);
        initializeBackGroundServiceForMakingLocationGraph();
    }

    public void put(final DriverDto driverData){
        if(driverMap.containsKey(driverData.getId())){
            LOG.info("Updating existing Driver" + driverData);
            Driver driver = driverMap.get(driverData.getId());
            driver.setLatitude(driverData.getLatitude());
            driver.setLongitude(driverData.getLongitude());
            driver.setAccuracy(driverData.getAccuracy());
        }else{
            LOG.info("Putting up new Driver" + driverData);
            Driver driver = Driver.fromDriverDto(driverData);
            driverMap.put(driver.getId(), driver);
            drivers.add(driver);
        }
    }

    public List<DriverDto> getNearbyDrivers(final CustomerDto customerDto){

        Customer customer = Customer.fromCustomerDto(customerDto);
        List<Driver> drivers = getDrivers(customer);
        List<DriverDto> driverDtos = new ArrayList<>();

        for(Driver driver : drivers){
            driverDtos.add(Driver.toDriverDto(driver));
        }

        return  driverDtos;
    }

    private List<Driver> getDrivers(final Customer customer) {
        return locationGraph.searchNearByDrivers(customer);
    }

    public void initializeBackGroundServiceForMakingLocationGraph(){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(locationGraph, 0, 60, TimeUnit.SECONDS);
    }


}
