package com.group15.tourassist.web.controller;

import com.group15.tourassist.entity.GuideMaster;
import com.group15.tourassist.service.IGuideMasterService;
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
