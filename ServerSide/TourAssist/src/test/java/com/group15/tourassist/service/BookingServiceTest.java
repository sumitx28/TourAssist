package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookedItem;
import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.Guest;
import com.group15.tourassist.repository.IBookingRepository;
import com.group15.tourassist.repository.IGuestRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private IBookingRepository bookingRepository;

    @Mock
    private BookingLineItemService bookingLineItemService;

    @Mock
    private GuestService guestService;

    @Mock
    private IGuestRepository guestRepository;

    private Booking confirmBooking;

    private BookingRequest bookingRequest;

    private BookingItemRequest bookingItemRequest;

    private Guest guest;

    private Booking pendingBooking;

    @BeforeEach
    public void setup() {
        confirmBooking = new Booking(1L, 1L, 2L, 3L, Instant.parse("2023-08-20T00:00:00Z"), 100D, BookingStatus.CONFIRM);
        bookingItemRequest = new BookingItemRequest(BookedItem.ACTIVITY, 1L);
        guest = new Guest(1L, null, "Raj", "Patel", Instant.parse("1996-02-11T00:00:00Z"));
        bookingRequest = new BookingRequest(1L, 2L, 3L, Collections.singletonList(bookingItemRequest), Collections.singletonList(guest));
        pendingBooking = new Booking(2L, 1L, 2L, 3L, Instant.parse("2023-08-20T00:00:00Z"), 100D, BookingStatus.PENDING);
    }

    @Test
    void createBooking_PositiveCase() {
        // Arrange
        when(bookingRepository.save(any())).thenReturn(confirmBooking);
        when(bookingLineItemService.computeTotalPrice(any())).thenReturn(10d);

        // Act
        Long bookingId = bookingService.createBooking(bookingRequest);

        // Assert
        assertEquals(1L, bookingId);   // should return a non null booking_id (1 in this case) which gets stored in db.
    }

    @Test
    void testGetBookingById() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.ofNullable(confirmBooking));

        // Act
        Booking savedBooking = bookingService.getBookingById(1L);

        // Assert
        assertEquals(confirmBooking, savedBooking);
    }

    @Test
    void testUpdateBookingStatus() {
        // Arrange
        doNothing().when(bookingRepository).updateBookingStatus(pendingBooking.getId(), "CONFIRM");

        // Act
        bookingService.updateBookingStatus(pendingBooking.getId(), TransactionStatus.SUCCESS);

        // Assert -- Verify that updateBookingStatus gets called 1 time.
        verify(bookingRepository,times(1)).updateBookingStatus(pendingBooking.getId(), "CONFIRM");
    }
}