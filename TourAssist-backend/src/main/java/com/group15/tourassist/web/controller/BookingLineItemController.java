package com.group15.tourassist.web.controller;
import com.group15.tourassist.service.impl.IBookingLineItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/revenue")
public class BookingLineItemController {

    Logger log = LoggerFactory.getLogger(PackageController.class);

    @Autowired
    private IBookingLineItemService bookingLineItemService;

    @GetMapping("/{type}/{agentId}")
    public ResponseEntity<Double> totalRevenueGenerated(@PathVariable String type, @PathVariable Long agentId) {
        log.info("** get total revenue generated");
        var  response = bookingLineItemService.totalBookingPriceByType(agentId, type);
        return ResponseEntity.of(Optional.of(response));
    }
}
