package com.group15.tourassist.web.controller;

import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.entity.GuideMaster;
import com.group15.tourassist.response.GuidesResponse;
import com.group15.tourassist.service.impl.IGuideMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class GuideMasterController {

    Logger log = LoggerFactory.getLogger(GuideMasterController.class);

    @Autowired
    private IGuideMasterService guideMasterService;

    @GetMapping("/guides/{destinationId}")
    private ResponseEntity<GuidesResponse> getSuites(@PathVariable Long destinationId) {
        log.info("Received request to get all guides for destination Id: " + destinationId);
        GuidesResponse response = new GuidesResponse(guideMasterService.getAllGuidesByLocation(destinationId));
        response.setMessage(ConstantUtils.SUCCESS);
        response.setStatusCode("200");
        log.info("The request to retrieve guides was handled.");
        return ResponseEntity.ok(response);
    }

}
