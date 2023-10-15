package com.group15.tourassist.web.controller;

import com.group15.tourassist.entity.GuideMaster;
import com.group15.tourassist.service.IGuideMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class GuideMasterController {

    Logger log = LoggerFactory.getLogger(GuideMasterController.class);

    @Autowired
    private IGuideMasterService guideMasterService;

    @GetMapping("/guides/{destinationId}")
    private ResponseEntity<List<GuideMaster>> getSuites(@PathVariable Long destinationId) {

        log.info("** get guides request");
        List<GuideMaster> guides = guideMasterService.getAllGuidesByLocation(destinationId);
        return ResponseEntity.of(Optional.of(guides));
    }

}
