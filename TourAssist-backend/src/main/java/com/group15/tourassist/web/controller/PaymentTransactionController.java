package com.group15.tourassist.web.controller;

import com.group15.tourassist.request.PaymentRequest;
import com.group15.tourassist.service.PaymentTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentTransactionController {

        Logger log = LoggerFactory.getLogger(PackageController.class);

        @Autowired
        private PaymentTransactionService paymentTransactionService;


        @PostMapping("/payment-transaction")
        private ResponseEntity<Long> createPayment(@RequestBody PaymentRequest request) {
                log.info("** get payment-transaction request {}", request.toString());

                Long bookingId = paymentTransactionService.createPayment(request);
                return ResponseEntity.of(Optional.of(bookingId));
        }
}
