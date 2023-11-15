package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.request.BookingRequest;
import com.group15.tourassist.response.BookingDetailsWebResponse;


public interface IBookingService {
    Long createBooking(BookingRequest bookingRequest);

    Booking getBookingById(Long bookingId);

    void updateBookingStatus(Long bookingId, TransactionStatus transactionStatus);

    BookingDetailsWebResponse getAllBookingForCustomer(Long appUserId);
}
