package com.group15.tourassist.web.controller;

import com.group15.tourassist.entity.TravelModeMaster;
import com.group15.tourassist.service.ITravelModeMasterService;
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
public class TravelModeMasterController {

    Logger log = LoggerFactory.getLogger(TravelModeMasterController.class);

    @Autowired
    ITravelModeMasterService travelModeMasterService;


    @GetMapping("/travel-modes")
    private ResponseEntity<List<TravelModeMaster>> getTravelModes() {
        log.info("** get travel-modes request");
        List<TravelModeMaster> allModes = travelModeMasterService.getAllTravelModes();
        return ResponseEntity.of(Optional.of(allModes));
    }
}
