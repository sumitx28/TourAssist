package com.group15.tourassist.service;

import com.group15.tourassist.request.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentTransactionService implements IPaymentTransactionService{



    /**
     * @param request Request object which contains payment details and status
     * @return Id of created payment entity.
     */
    @Override
    public Long createPayment(PaymentRequest request) {

        return null;
    }
}
