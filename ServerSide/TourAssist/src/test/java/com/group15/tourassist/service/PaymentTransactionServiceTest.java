package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.repository.IPaymentTransactionRepository;
import com.group15.tourassist.request.PaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

    @InjectMocks
    private PaymentTransactionService paymentTransactionService;

    @Mock
    private IPaymentTransactionRepository paymentTransactionRepository;

    private PaymentRequest paymentRequest;

    @BeforeEach
    public void setup() {
        paymentRequest = new PaymentRequest("d8b349b0-80b3-11ee-b962-0242ac120002", 1L, "Credit Card", TransactionStatus.SUCCESS);
    }

    @Test
    void createPayment() {
        // Act
        Long paymentId = paymentTransactionService.createPayment(paymentRequest);

        // Assert
        assertEquals(1L, paymentId);
    }
}