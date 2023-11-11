package com.group15.tourassist.service;

import com.group15.tourassist.repository.*;
import com.group15.tourassist.request.BookingItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingLineItemService implements IBookingLineItemService{

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

            switch (bookingItemRequest.getItemName()) {
                case ACTIVITY:
                    totalPrice += activityRepository.findById(bookingItemRequest.getItemId()).get().getPrice();
                    break;
                case TRANSPORTATION:
                    totalPrice += transportationRepository.findById(bookingItemRequest.getItemId()).get().getPrice();
                    break;
                case GUIDE:
                    totalPrice += tourGuideRepository.findById(bookingItemRequest.getItemId()).get().getPrice();
                    break;
                case RESORT:
                    totalPrice += stayRepository.findById(bookingItemRequest.getItemId()).get().getPrice();
                    break;
            }
        }
        return totalPrice;
    }
}
