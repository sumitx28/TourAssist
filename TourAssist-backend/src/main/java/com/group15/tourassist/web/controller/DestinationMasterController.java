package com.group15.tourassist.web.controller;

import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.response.LocationsResponse;
import com.group15.tourassist.service.impl.IDestinationMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class DestinationMasterController {

    Logger log = LoggerFactory.getLogger(DestinationMasterController.class);

    @Autowired
    IDestinationMasterService destinationMasterService;

    @GetMapping("/locations")
    private ResponseEntity<LocationsResponse> getLocations() {
        log.info("Received request to get all locations");
        LocationsResponse response = new LocationsResponse(destinationMasterService.getAllLocations());
        response.setMessage(ConstantUtils.SUCCESS);
        response.setStatusCode("200");
        log.info("The request to retrieve locations was handled.");
        return ResponseEntity.ok(response);
    }
}
