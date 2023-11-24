package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.entity.PaymentTransaction;
import com.group15.tourassist.repository.IPackageRepository;
import com.group15.tourassist.repository.IPaymentTransactionRepository;
import com.group15.tourassist.request.PaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

    @InjectMocks
    private PaymentTransactionService paymentTransactionService;

    @Mock
    private IPaymentTransactionRepository paymentTransactionRepository;

    @Mock
    private BookingService bookingService;

    @Mock
    private IPackageRepository packageRepository;

    @Mock
    private EmailService emailService;

    private PaymentRequest paymentRequest;

    @Mock
    private Booking booking;


    private Package package1;

    private PaymentTransaction paymentTransaction;

    @BeforeEach
    public void setup() {
        package1 = new Package();

        booking = Booking.builder()
                .id(1L)
                .packageId(1L)
                .customerId(2L)
                .agentId(3L)
                .bookingDate(Instant.parse("2023-08-20T00:00:00Z"))
                .totalPrice(100D)
                .bookingStatus(BookingStatus.CONFIRM)
                .build();

        paymentTransaction =  PaymentTransaction.builder()
                .id(1L)
                .transactionId("d8b349b0-80b3-11ee-b962-0242ac120002")
                .booking(booking)
                .transactionStatus(TransactionStatus.SUCCESS)
                .price(100D)
                .paymentType("Credit Card")
                .transactionDate(Instant.now())
                .build();

        paymentRequest = PaymentRequest.builder()
                .customerEmail("r.patel@dal.ca")
                .transactionId("d8b349b0-80b3-11ee-b962-0242ac120002")
                .bookingId(1L)
                .paymentType("Credit Card")
                .transactionStatus(TransactionStatus.SUCCESS)
                .price(100D)
                .build();

    }

    @Test
    void testCreatePayment() {
        // Arrange
        when(bookingService.getBookingById(1L)).thenReturn(booking);
        when(packageRepository.findById(1L)).thenReturn(Optional.ofNullable(package1));
        when(paymentTransactionRepository.save(any())).thenReturn(paymentTransaction);

        // Act
        Long paymentId = paymentTransactionService.createPayment(paymentRequest);

        // Assert
        assertEquals(1L, paymentId);
    }
}