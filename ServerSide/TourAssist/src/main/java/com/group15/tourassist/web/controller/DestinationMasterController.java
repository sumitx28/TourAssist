package com.group15.tourassist.web.controller;

import com.group15.tourassist.entity.DestinationMaster;
import com.group15.tourassist.service.IDestinationMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(
        origins = {
                "http://localhost:5173",
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })

@RestController
@RequestMapping("/api/v1")
public class DestinationMasterController {

    Logger log = LoggerFactory.getLogger(DestinationMasterController.class);

    @Autowired
    IDestinationMasterService destinationMasterService;


    @GetMapping("/locations")
    private ResponseEntity<List<DestinationMaster>> getLocations() {
        log.info("** get locations request");
        List<DestinationMaster> allLocations = destinationMasterService.getAllLocations();
        return ResponseEntity.of(Optional.of(allLocations));
    }
}
