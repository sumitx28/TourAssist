package com.group15.tourassist.service;

import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.entity.PaymentTransaction;
import com.group15.tourassist.repository.IPackageRepository;
import com.group15.tourassist.repository.IPaymentTransactionRepository;
import com.group15.tourassist.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class PaymentTransactionService implements IPaymentTransactionService{

    @Autowired
    private IPaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IPackageRepository packageRepository;

    /**
     * @param request Request object which contains payment details and status
     * @return Id of created payment entity.
     */
    @Override
    @Transactional
    public Long createPayment(PaymentRequest request) {
        // create entity object to save
        Booking booking = bookingService.getBookingById(request.getBookingId());
        Optional<Package> bookedPackage = packageRepository.findById(booking.getPackageId());
        PaymentTransaction paymentTransaction = PaymentTransaction.getPaymentByRequest(request, booking);

        // persist paymentTransaction
        paymentTransaction = paymentTransactionRepository.save(paymentTransaction);

        // update booking status
        bookingService.updateBookingStatus(booking.getId(), request.getTransactionStatus());

        // send confirmation/failure email
        sendBookingEmail(request.getCustomerEmail(), booking, bookedPackage.get(), paymentTransaction);

        return paymentTransaction.getId();
    }

    /**
     * @param customerEmail customer emailId
     * @param booking booking object for confirmation
     * @param bookedPackage package details
     * @param paymentTransaction transaction details
     */
    private void sendBookingEmail(String customerEmail, Booking booking, Package bookedPackage, PaymentTransaction paymentTransaction) {
        String emailBody = emailService.frameBookingEmail(booking, bookedPackage, paymentTransaction);
        emailService.sendEmail(customerEmail, "Booking #" + booking.getId() , emailBody);
    }
}
