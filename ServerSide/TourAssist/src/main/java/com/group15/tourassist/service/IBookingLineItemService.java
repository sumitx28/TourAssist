package com.group15.tourassist.service;

import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.BookingLineItem;
import com.group15.tourassist.request.BookingItemRequest;

import java.util.List;

public interface IBookingLineItemService {
    
    Double computeTotalPrice(List<BookingItemRequest> bookingItemRequests);

    void createBookingLineItems(List<BookingItemRequest> bookingItemRequests, Booking booking);

    List<BookingLineItem> getBookingLineItemsByBookingId(Long bookingId);

    Double totalBookingPriceByType(Long agentId, String type);
}
