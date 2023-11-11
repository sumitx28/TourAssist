package com.group15.tourassist.web.controller;

import com.group15.tourassist.request.BookingRequest;
import com.group15.tourassist.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/v1/booking")
public class BookingController {

        Logger log = LoggerFactory.getLogger(PackageController.class);

        @Autowired
        private BookingService bookingService;

        /**
         * @param request booking request to create a booking
         * @return booking id
         */
        @PostMapping("/create-booking")
        private ResponseEntity<Long> createBooking(@RequestBody BookingRequest request) {
                log.info("** get create-booking request {}", request.toString());

                Long bookingId = bookingService.createBooking(request);
                return ResponseEntity.of(Optional.of(bookingId));
        }
}
