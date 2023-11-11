package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.repository.IBookingRepository;
import com.group15.tourassist.request.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService implements IBookingService{

    @Autowired
    private IBookingLineItemService bookingLineItemService;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IGuestService guestService;


    /**
     * @param bookingRequest request to create booking for
     * @return booking id
     */
    @Override
    public Long createBooking(BookingRequest bookingRequest) {
        // compute total price
        Double totalPrice = bookingLineItemService.computeTotalPrice(bookingRequest.getBookingItemRequests());

        // persist booking entity
        Booking booking = Booking.getBookingFromRequest(bookingRequest, totalPrice, BookingStatus.PENDING);
        booking = bookingRepository.save(booking);

        // persist booking line items
        bookingLineItemService.createBookingLineItems(bookingRequest.getBookingItemRequests(), booking);

        // persists guests
        guestService.createGuests(bookingRequest.getGuests(), booking);

        return booking.getId();
    }


    /**
     * @param bookingId bookingId to be queried
     * @return Booking entity corresponding to the id
     */
    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId).get();
    }

    /**
     * @param bookingId bookingId for which status should be updated
     * @param transactionStatus corresponding transaction status
     */
    @Override
    public void updateBookingStatus(Long bookingId, TransactionStatus transactionStatus) {
        BookingStatus bookingStatus = transactionStatus.equals(TransactionStatus.SUCCESS) ? BookingStatus.CONFIRM : BookingStatus.PENDING;
        bookingRepository.updateBookingStatus(bookingId, bookingStatus.toString());
    }
}
