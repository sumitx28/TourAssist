package com.group15.tourassist.service;

import com.group15.tourassist.request.BookingRequest;
import org.springframework.stereotype.Service;

@Service
public class BookingService implements IBookingService{

    /**
     * @param bookingRequest request to create booking for
     * @return booking id
     */
    @Override
    public Long createBooking(BookingRequest bookingRequest) {
        return null;
    }
}
