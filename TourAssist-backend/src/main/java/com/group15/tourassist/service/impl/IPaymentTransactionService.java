package com.group15.tourassist.service.impl;

import com.group15.tourassist.request.PaymentRequest;

public interface IPaymentTransactionService {
    Long createPayment(PaymentRequest request);
}
