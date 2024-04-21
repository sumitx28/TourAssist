package com.group15.tourassist.service.impl;

import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.request.BookingRequest;
import com.group15.tourassist.response.BookingDetailsWebResponse;
import com.group15.tourassist.response.BookingResponse;
import com.group15.tourassist.response.CustomerDetailsBookedByAgentIDResponse;

import java.util.List;


public interface IBookingService {
    Long createBooking(BookingRequest bookingRequest);

    Booking getBookingById(Long bookingId);

    void updateBookingStatus(Long bookingId, TransactionStatus transactionStatus);

    BookingDetailsWebResponse getAllBookingForCustomer(Long appUserId);

    List<BookingResponse> getPastBookings(Long agentId);

    List<BookingResponse> getUpcomingBookings(Long agentId);

    List<CustomerDetailsBookedByAgentIDResponse> getCustomersBookedByAgentID(Long agentId);

}
