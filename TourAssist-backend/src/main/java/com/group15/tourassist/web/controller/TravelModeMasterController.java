package com.group15.tourassist.web.controller;

import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.entity.TravelModeMaster;
import com.group15.tourassist.response.TravelModesResponse;
import com.group15.tourassist.service.impl.ITravelModeMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TravelModeMasterController {
    Logger log = LoggerFactory.getLogger(TravelModeMasterController.class);

    @Autowired
    ITravelModeMasterService travelModeMasterService;

    @GetMapping("/travel-modes")
    private ResponseEntity<TravelModesResponse> getTravelModes() {
        log.info("Received request to get all travel modes");
        TravelModesResponse response = new TravelModesResponse(travelModeMasterService.getAllTravelModes());
        response.setMessage(ConstantUtils.SUCCESS);
        response.setStatusCode("200");
        log.info("The request to retrieve travel modes was handled.");
        return ResponseEntity.ok(response);
    }
}
