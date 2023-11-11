package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookedItem;
import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.Guest;
import com.group15.tourassist.repository.IBookingRepository;
import com.group15.tourassist.request.BookingItemRequest;
import com.group15.tourassist.request.BookingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private IBookingRepository bookingRepository;

    @Mock
    private BookingLineItemService bookingLineItemService;

    private Booking confirmBooking;

    private BookingRequest bookingRequest;

    private BookingItemRequest bookingItemRequest;

    private Guest guest;

    @BeforeEach
    public void setup() {
        confirmBooking = new Booking(1l, 1l, 2l, 3l, Instant.parse("2023-08-20T00:00:00Z"), 100d, BookingStatus.CONFIRM);
        bookingItemRequest = new BookingItemRequest(BookedItem.ACTIVITY, 1l);
        guest = new Guest(1l, null, "Raj", "Patel", Instant.parse("1996-02-11T00:00:00Z"));
        bookingRequest = new BookingRequest(1l, 2l, 3l, Collections.singletonList(bookingItemRequest), Collections.singletonList(guest));
    }

    @Test
    void createBooking_PositiveCase() {
        // Arrange
        when(bookingRepository.save(any())).thenReturn(confirmBooking);
        when(bookingLineItemService.computeTotalPrice(any())).thenReturn(10d);

        // Act
        Long bookingId = bookingService.createBooking(bookingRequest);

        // Assert
        assertNotNull(bookingId);   // should return a non null booking_id which gets stored in db.
    }
}