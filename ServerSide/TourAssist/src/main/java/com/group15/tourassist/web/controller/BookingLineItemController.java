package com.group15.tourassist.web.controller;
import com.group15.tourassist.request.BookingRequest;
import com.group15.tourassist.response.BookingDetailsWebResponse;
import com.group15.tourassist.response.BookingResponse;
import com.group15.tourassist.response.CustomerDetailsBookedByAgentIDResponse;
import com.group15.tourassist.service.BookingService;
import com.group15.tourassist.service.IBookingLineItemService;
import com.group15.tourassist.service.IBookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
