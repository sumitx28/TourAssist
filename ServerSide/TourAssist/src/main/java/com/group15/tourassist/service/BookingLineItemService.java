package com.group15.tourassist.service;

import com.group15.tourassist.repository.IBookingLineItemRepository;
import com.group15.tourassist.request.BookingItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingLineItemService implements IBookingLineItemService{

    @Autowired
    private IBookingLineItemRepository bookingLineItemRepository;

    /**
     * @param bookingItemRequests items included in this booking request
     * @return total price of the items added up.
     */
    @Override
    public Double computeTotalPrice(List<BookingItemRequest> bookingItemRequests) {
        return null;
    }
}
