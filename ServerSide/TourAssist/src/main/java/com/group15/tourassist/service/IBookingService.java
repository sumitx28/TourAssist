package com.group15.tourassist.service;

import com.group15.tourassist.request.BookingRequest;

public interface IBookingService {
    Long createBooking(BookingRequest bookingRequest);
}
