package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.request.BookingRequest;
import com.group15.tourassist.response.BookingResponse;
import java.util.List;
import com.group15.tourassist.response.BookingDetailsWebResponse;


public interface IBookingService {
    Long createBooking(BookingRequest bookingRequest);

    Booking getBookingById(Long bookingId);

    void updateBookingStatus(Long bookingId, TransactionStatus transactionStatus);

    List<BookingResponse> getPastBookings(Long agentId);

    List<BookingResponse> getUpcomingBookings(Long agentId);
    BookingDetailsWebResponse getAllBookingForCustomer(Long appUserId);
}
