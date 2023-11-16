package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.dto.BookingDTO;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.request.BookingRequest;
import com.group15.tourassist.response.BookingResponse;

import java.util.Date;
import java.util.List;


public interface IBookingService {
    Long createBooking(BookingRequest bookingRequest);

    Booking getBookingById(Long bookingId);

    void updateBookingStatus(Long bookingId, TransactionStatus transactionStatus);

    List<BookingResponse> getPastBookings(Date bookingDate);

    List<BookingResponse> getUpcomingBookings(Date bookingDate);
}
