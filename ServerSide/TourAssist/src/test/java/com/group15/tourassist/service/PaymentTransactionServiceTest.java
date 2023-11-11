package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.repository.IPaymentTransactionRepository;
import com.group15.tourassist.request.PaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

    @InjectMocks
    private PaymentTransactionService paymentTransactionService;

    @Mock
    private IPaymentTransactionRepository paymentTransactionRepository;

    @Mock
    private BookingService bookingService;

    private PaymentRequest paymentRequest;

    @Mock
    private Booking booking;

    @BeforeEach
    public void setup() {
        paymentRequest = new PaymentRequest("d8b349b0-80b3-11ee-b962-0242ac120002", 1L, "Credit Card", TransactionStatus.SUCCESS, 100D);
    }

    @Test
    void createPayment() {
        // Arrange
        when(bookingService.getBookingById(1L)).thenReturn(booking);

        // Act
        Long paymentId = paymentTransactionService.createPayment(paymentRequest);

        // Assert
        assertEquals(1L, paymentId);
    }
}