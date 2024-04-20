package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.entity.PaymentTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Instant;

import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private EmailService emailService;
    @InjectMocks
    private EmailService emailSenderService;

    @Mock
    private JavaMailSender javaMailSender;

    private Booking booking;

    private Package confirmPackage;

    private PaymentTransaction paymentTransaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        booking = new Booking(1L, 1L, 2L, 3L, Instant.parse("2023-08-20T00:00:00Z"), 100D, BookingStatus.CONFIRM);
        confirmPackage = new Package();
        confirmPackage.setPackageName("Test package");
        confirmPackage.setTripStartDate(Instant.parse("2023-08-25T00:00:00Z"));
        confirmPackage.setTripEndDate(Instant.parse("2023-08-30T00:00:00Z"));

        paymentTransaction = PaymentTransaction.builder()
                .id(1L)
                .transactionId("d8b349b0-80b3-11ee-b962-0242ac120002")
                .booking(booking)
                .transactionStatus(TransactionStatus.SUCCESS)
                .price(100D)
                .paymentType("Credit Card")
                .transactionDate(Instant.now())
                .build();
    }


    @Test
    void testSendBookingEmail() {
        // Arrange -- expected output should be of this format and text for the given parameters.
        String expectedOutput = "Thank you for booking Test package. Your booking is CONFIRMED. Below are the details:\n" +
                "Booking Id: 1\n" +
                "Payment Id: d8b349b0-80b3-11ee-b962-0242ac120002\n" +
                "Trip duration: 2023-08-25T00:00:00Z - 2023-08-30T00:00:00Z";
        doNothing().when(emailService).sendBookingEmail(isA(String.class), isA(Booking.class), isA(Package.class), isA(PaymentTransaction.class));

        //Act
        emailService.sendBookingEmail("abc@gmail.com", booking, confirmPackage, paymentTransaction);

        // Assert
        verify(emailService, times(1)).sendBookingEmail("abc@gmail.com", booking, confirmPackage, paymentTransaction);
    }

    @Test
    void sendEmail_ShouldSendEmailSuccessfully() {
        // Arrange
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        // Act
        emailSenderService.sendEmail(to, subject, body);

        // Assert
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

}