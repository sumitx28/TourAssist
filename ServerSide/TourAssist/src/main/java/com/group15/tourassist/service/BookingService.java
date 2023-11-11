package com.group15.tourassist.service;

import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.request.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService implements IBookingService{

    @Autowired
    private IBookingLineItemService bookingLineItemService;



    /**
     * @param bookingRequest request to create booking for
     * @return booking id
     */
    @Override
    public Long createBooking(BookingRequest bookingRequest) {
        Double totalPrice = bookingLineItemService.computeTotalPrice(bookingRequest.getBookingItemRequests());

        return null;
    }
}
