package com.group15.tourassist.web.controller;

import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.entity.SuiteMaster;
import com.group15.tourassist.response.SuitesResponse;
import com.group15.tourassist.service.impl.ISuiteMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class SuiteMasterController {

    Logger log = LoggerFactory.getLogger(SuiteMasterController.class);

    @Autowired
    private ISuiteMasterService suiteMasterService;

    @GetMapping("/suites")
    private ResponseEntity<SuitesResponse> getSuites() {
        log.info("Received request to get all suites");
        SuitesResponse response = new SuitesResponse(suiteMasterService.findAllSuites());
        response.setMessage(ConstantUtils.SUCCESS);
        response.setStatusCode("200");
        log.info("The request to retrieve suites was handled.");
        return ResponseEntity.ok(response);
    }
}
