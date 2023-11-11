package com.group15.tourassist.service;

import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.PaymentTransaction;
import com.group15.tourassist.repository.IPaymentTransactionRepository;
import com.group15.tourassist.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentTransactionService implements IPaymentTransactionService{

    @Autowired
    private IPaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private BookingService bookingService;

    /**
     * @param request Request object which contains payment details and status
     * @return Id of created payment entity.
     */
    @Override
    public Long createPayment(PaymentRequest request) {
        // create entity object to save
        Booking booking = bookingService.getBookingById(request.getBookingId());
        PaymentTransaction paymentTransaction = PaymentTransaction.getPaymentByRequest(request, booking);

        // persist paymentTransaction
        paymentTransaction = paymentTransactionRepository.save(paymentTransaction);

        // update booking status
        bookingService.updateBookingStatus(booking.getId(), request.getTransactionStatus());

        // send confirmation/failure email

        return null;
    }
}
