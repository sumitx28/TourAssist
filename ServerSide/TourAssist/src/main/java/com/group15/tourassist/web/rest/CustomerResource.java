package com.group15.tourassist.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/customers")
    public String getAllCustomers() {
        // TODO: fetch all customers
        logger.info( "customers");
        return "customers";
    }
}
