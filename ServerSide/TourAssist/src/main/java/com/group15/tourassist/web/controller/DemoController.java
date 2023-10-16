package com.group15.tourassist.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    Logger log = LoggerFactory.getLogger(DemoController.class);


    @GetMapping
    public ResponseEntity<String> sayHello() {
        log.info("** inside hello from secure point request");
        return ResponseEntity.ok("Hello from secured endpoint");
    }

}
