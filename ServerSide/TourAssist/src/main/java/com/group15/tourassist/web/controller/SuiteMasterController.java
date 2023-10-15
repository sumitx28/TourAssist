package com.group15.tourassist.web.controller;

import com.group15.tourassist.entity.SuiteMaster;
import com.group15.tourassist.service.ISuiteMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class SuiteMasterController {

    Logger log = LoggerFactory.getLogger(SuiteMasterController.class);

    @Autowired
    private ISuiteMasterService suiteMasterService;

    @GetMapping("/suites")
    private ResponseEntity<List<SuiteMaster>> getSuites() {
        log.info("** get suites request");
        List<SuiteMaster> suites = suiteMasterService.findAllSuites();
        return ResponseEntity.of(Optional.of(suites));
    }
}
