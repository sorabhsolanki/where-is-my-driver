package com.locate.driver.connector;

import com.locate.driver.dto.DriverDto;
import com.locate.driver.exception.BadRequestException;
import com.locate.driver.service.LocationService;
import com.locate.driver.valid.ValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/whereismydriver")
public class LocateMyDriverConnector {

    private static final Logger LOG = LoggerFactory.getLogger(LocateMyDriverConnector.class);

    @Autowired
    LocationService locationService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(){
		return "Locate My Driver is up and running";
	}

    @RequestMapping(value = "/drivers/{latitude}/{longitude}/{radius}/{limit}", method = RequestMethod.GET)
    public ResponseEntity drivers(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
                          @PathVariable("radius") double radius, @PathVariable("limit") int limit){
        List<DriverDto> driverDtos = null;
        try {
            driverDtos = locationService.getDrivers(latitude, longitude, radius, limit);
        } catch (BadRequestException be) {
            ValidationMessage validationMessage = new ValidationMessage();
            validationMessage.getErrors().add("Latitude should be between +/- 90");
            return new ResponseEntity(validationMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<DriverDto>>(driverDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/drivers/{id}/location", method = RequestMethod.PUT)
    public ResponseEntity<String> addOrUpdateDriver(@PathVariable("id") int id, @RequestBody DriverDto driverDto){
        if(id < 0 || id > 50000)
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        driverDto.setId(id);
        try {
            locationService.addOrUpdateDriver(driverDto);
        } catch (BadRequestException e) {
            ValidationMessage validationMessage = new ValidationMessage();
            validationMessage.getErrors().add("Latitude should be between +/- 90");
            return new ResponseEntity(validationMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }


}
