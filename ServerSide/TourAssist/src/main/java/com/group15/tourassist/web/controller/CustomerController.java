package com.group15.tourassist.web.controller;

import com.group15.tourassist.entity.Customer;
import com.group15.tourassist.service.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(
        origins = {
                "*",
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
public class CustomerController {
    Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    ICustomerService customerService;

    @GetMapping("/customer/{appUserId}")
    private ResponseEntity<Customer> getCustomerDetails(@PathVariable Long appUserId) {
        log.info("** get customer request for app_user_id {}", appUserId);

        Customer customer = customerService.getCustomerByAppUserId(appUserId);
        return ResponseEntity.of(Optional.of(customer));
    }
}
