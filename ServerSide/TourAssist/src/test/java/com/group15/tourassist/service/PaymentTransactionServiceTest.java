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
        booking = new Booking(1L, 1L, 2L, 3L, Instant.parse("2023-08-20T00:00:00Z"), 100D, BookingStatus.CONFIRM);
        paymentTransaction = new PaymentTransaction(1L, "d8b349b0-80b3-11ee-b962-0242ac120002", booking, TransactionStatus.SUCCESS, 100D, "Credit Card", Instant.now());
        paymentRequest = new PaymentRequest("r.patel@dal.ca", "d8b349b0-80b3-11ee-b962-0242ac120002", 1L, "Credit Card", TransactionStatus.SUCCESS, 100D);
        package1 = new Package();
    }

    @Test
    void createPayment() {
        // Arrange
        when(bookingService.getBookingById(1L)).thenReturn(booking);
        when(packageRepository.findById(1L)).thenReturn(Optional.ofNullable(package1));
        when(emailService.frameBookingEmail(any(), any(), any())).thenReturn(new String());
        when(paymentTransactionRepository.save(any())).thenReturn(paymentTransaction);

        // Act
        Long paymentId = paymentTransactionService.createPayment(paymentRequest);

        // Assert
        assertEquals(1L, paymentId);
    }
}