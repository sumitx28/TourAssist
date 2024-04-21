package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookedItem;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.BookingLineItem;
import com.group15.tourassist.repository.*;
import com.group15.tourassist.request.BookingItemRequest;
import com.group15.tourassist.service.impl.IBookingLineItemService;
import com.group15.tourassist.web.controller.PackageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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


    Logger log = LoggerFactory.getLogger(PackageController.class);

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

    @Override
    public Double totalBookingPriceByType(Long agentId, String type) {
        Double total = (double) 0;
        log.info(type);
        if(type.equals("last_month")){
            Date toDate= Date.valueOf(LocalDate.now());
            Date fromDate=Date.valueOf(LocalDate.now().minusMonths(1));
            List<Double> prices= bookingLineItemRepository.getBookingPliForADuration(fromDate, toDate, agentId);
            log.info(prices.size()+"price");
            for(Double price: prices){
                total=total+price;
                log.info("price"+price);
            }
            return total;
        }
        else if(Objects.equals(type, "last_year")){
            Date toDate= Date.valueOf(LocalDate.now());
            Date fromDate=Date.valueOf(LocalDate.now().minusYears(1));
            List<Double> prices= bookingLineItemRepository.getBookingPliForADuration(fromDate, toDate, agentId);
            for(Double price: prices){
                total=total+price;
            }
            return total;
        }
        else{
            return total;
        }
    }
}
