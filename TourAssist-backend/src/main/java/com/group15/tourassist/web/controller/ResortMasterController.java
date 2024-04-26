package com.group15.tourassist.web.controller;

import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.entity.ResortMaster;
import com.group15.tourassist.response.ResortsResponse;
import com.group15.tourassist.service.impl.IResortMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ResortMasterController {

    Logger log = LoggerFactory.getLogger(ResortMasterController.class);

    @Autowired
    private IResortMasterService resortMasterService;

    @GetMapping("/resorts/{destinationId}")
    private ResponseEntity<ResortsResponse> getResorts(@PathVariable Long destinationId) {
        log.info("Received request to get all resorts.");
        ResortsResponse response = new ResortsResponse(resortMasterService.getAllResortsByDestinationId(destinationId));
        response.setMessage(ConstantUtils.SUCCESS);
        response.setStatusCode("200");
        log.info("The request to retrieve resorts was handled.");
        return ResponseEntity.ok(response);
    }
}
