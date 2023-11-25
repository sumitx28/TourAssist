package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.entity.PaymentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

    /**
     * @param booking booking for which email needs to be sent
     * @param bookedPackage package details
     * @param paymentTransaction payment transaction details
     */
    protected String frameBookingEmail(Booking booking, Package bookedPackage, PaymentTransaction paymentTransaction) {
        String bookingStatus = "FAILED";
        if(paymentTransaction.getTransactionStatus().equals(TransactionStatus.SUCCESS)) {
            bookingStatus = "CONFIRMED";
        }

        // parse BOOKING_EMAIL_TEMPLATE and create appropriate content
        String packageName = bookedPackage.getPackageName();
        String tranxId = paymentTransaction.getTransactionId();
        Instant tripStart =  bookedPackage.getTripStartDate();
        Instant tripEnd =  bookedPackage.getTripEndDate();

        Object[] args = {packageName, bookingStatus, booking.getId(), tranxId, tripStart, tripEnd};
        String emailContent = String.format(ConstantUtils.BOOKING_EMAIL_TEMPLATE, args);

        return emailContent;
    }


    /**
     * @param customerEmail customer emailId
     * @param booking booking object for confirmation
     * @param bookedPackage package details
     * @param paymentTransaction transaction details
     */
    public void sendBookingEmail(String customerEmail, Booking booking, Package bookedPackage, PaymentTransaction paymentTransaction) {
        String emailBody = frameBookingEmail(booking, bookedPackage, paymentTransaction);
        sendEmail(customerEmail, "Booking #" + booking.getId() , emailBody);
    }
}
