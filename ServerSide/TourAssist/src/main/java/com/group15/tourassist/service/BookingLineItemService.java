package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookedItem;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.BookingLineItem;
import com.group15.tourassist.repository.*;
import com.group15.tourassist.request.BookingItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingLineItemService implements IBookingLineItemService {

    @Autowired
    private IBookingLineItemRepository bookingLineItemRepository;

    @Autowired
    private IActivityRepository activityRepository;

    @Autowired
    private ITourGuideRepository tourGuideRepository;

    @Autowired
    private IStayRepository stayRepository;

    @Autowired
    private ITransportationRepository transportationRepository;


    /**
     * @param bookingItemRequests items included in this booking request
     * @return total price of the items added up.
     */
    @Override
    public Double computeTotalPrice(List<BookingItemRequest> bookingItemRequests) {
        Double totalPrice = 0D;
        for (BookingItemRequest bookingItemRequest : bookingItemRequests) {
            totalPrice += getLineItemPrice(bookingItemRequest.getItemName(), bookingItemRequest.getItemId());
        }
        return totalPrice;
    }

    /**
     * @param bookedItem Type of custom item which is getting booked
     * @param itemId     ID of the custom item (activity, guide, transportation, resort)
     * @return price of that item.
     */
    private Double getLineItemPrice(BookedItem bookedItem, Long itemId) {
        switch (bookedItem) {
            case ACTIVITY:
                return activityRepository.findById(itemId).get().getPrice();
            case TRANSPORTATION:
                return transportationRepository.findById(itemId).get().getPrice();
            case GUIDE:
                return tourGuideRepository.findById(itemId).get().getPrice();
            case RESORT:
                return stayRepository.findById(itemId).get().getPrice();
        }
        return 0D;
    }

    /**
     * @param bookingItemRequests
     * @param booking
     */
    @Override
    public void createBookingLineItems(List<BookingItemRequest> bookingItemRequests, Booking booking) {
        for (BookingItemRequest bookingRequest : bookingItemRequests) {
            Double price = getLineItemPrice(bookingRequest.getItemName(), bookingRequest.getItemId());
            BookingLineItem bookingLineItem = BookingLineItem.getBookingLineItem(bookingRequest, booking, price);
            bookingLineItemRepository.save(bookingLineItem);
        }
    }


    @Override
    public List<BookingLineItem> getBookingLineItemsByBookingId(Long bookingId) {
        return bookingLineItemRepository.getLineItemsByBookingId(bookingId);
    }
}
