package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.dto.BookingDTO;
import com.group15.tourassist.entity.Activity;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entityToDto.AgentEntityToDto;
import com.group15.tourassist.entityToDto.BookingEntityToDto;
import com.group15.tourassist.entityToDto.CustomerEntityToDto;
import com.group15.tourassist.entityToDto.PackageEntityToDto;
import com.group15.tourassist.repository.IAgentRepository;
import com.group15.tourassist.repository.IBookingRepository;
import com.group15.tourassist.repository.ICustomerRepository;
import com.group15.tourassist.repository.IPackageRepository;
import com.group15.tourassist.request.BookingRequest;
import com.group15.tourassist.response.BookingResponse;
import com.group15.tourassist.web.controller.PackageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService implements IBookingService{
    Logger log = LoggerFactory.getLogger(PackageController.class);

    @Autowired
    IBookingLineItemService bookingLineItemService;

    @Autowired
    IBookingRepository bookingRepository;

    @Autowired
    IPackageRepository packageRepository;

    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    IAgentRepository agentRepository;

    @Autowired
    private IGuestService guestService;

    @Autowired
    BookingEntityToDto bookingEntityToDto;

    @Autowired
    PackageEntityToDto packageEntityToDto;

    @Autowired
    CustomerEntityToDto customerEntityToDto;

    @Autowired
    AgentEntityToDto agentEntityToDto;
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

    @Override
    public List<BookingResponse> getPastBookings(Long agentId) {
        java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
        List<BookingResponse> response= new ArrayList<>();
        List<Booking> allBookings= bookingRepository.findAllByPastBookingDates(date, agentId);
        for (Booking booking:allBookings) {
            BookingResponse bookingResponse= new BookingResponse();
            bookingResponse.setId(booking.getId());
            bookingResponse.setPackageD(packageEntityToDto.packageEntityToDto(packageRepository.findById(booking.getPackageId()).get()));
            log.info("completed package");
            log.info(packageRepository.findById(booking.getPackageId()).get().toString());
            bookingResponse.setCustomer(customerEntityToDto.customerEntityToDto(customerRepository.findById(booking.getCustomerId()).get()));
            log.info("completed customer");

            bookingResponse.setAgent(agentEntityToDto.agentEntityToDto(agentRepository.findById(booking.getAgentId()).get()));
            log.info("3");

            bookingResponse.setBookingDate(booking.getBookingDate());
            log.info("4");

            bookingResponse.setTotalPrice(booking.getTotalPrice());
            log.info("5");

            bookingResponse.setBookingStatus(booking.getBookingStatus());
            log.info("6");
            response.add(bookingResponse);
        }
        log.info(response.toString());
        return response;
    }

    @Override
    public List<BookingResponse> getUpcomingBookings(Long agentId) {
        java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
        List<BookingResponse> response= new ArrayList<>();
        List<Booking> allBookings= bookingRepository.findAllByUpcomingBookingDates(date,agentId);
        for (Booking booking1:allBookings
        ) {
            BookingResponse bookingResponse= new BookingResponse();
            bookingResponse.setId(booking1.getId());
            bookingResponse.setPackageD(packageEntityToDto.packageEntityToDto(packageRepository.findById(booking1.getPackageId()).get()));
            bookingResponse.setCustomer(customerEntityToDto.customerEntityToDto(customerRepository.findById(booking1.getCustomerId()).get()));
            bookingResponse.setAgent(agentEntityToDto.agentEntityToDto(agentRepository.findById(booking1.getAgentId()).get()));
            bookingResponse.setBookingDate(booking1.getBookingDate());
            bookingResponse.setTotalPrice(booking1.getTotalPrice());
            bookingResponse.setBookingStatus(booking1.getBookingStatus());
            response.add(bookingResponse);
        }
        return response;
    }

}
