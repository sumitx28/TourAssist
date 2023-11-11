package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookedItem;
import com.group15.tourassist.entity.Activity;
import com.group15.tourassist.repository.IActivityRepository;
import com.group15.tourassist.repository.IBookingLineItemRepository;
import com.group15.tourassist.request.BookingItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookingLineItemServiceTest {

    @InjectMocks
    private BookingLineItemService bookingLineItemService;

    @Mock
    private IBookingLineItemRepository bookingLineItemRepository;

    @Mock
    private IActivityRepository activityRepository;

    private List<BookingItemRequest> bookingItems;


    private Activity activity1;
    private Activity activity2;

    @BeforeEach
    public void setup() {
        activity1 = new Activity(1l, 1l, 1l, Instant.parse("2023-08-01T00:00:00Z"), Instant.parse("2023-08-01T00:00:00Z"), Instant.parse("2023-08-01T00:00:00Z"), 100d, Boolean.TRUE);
        activity2 = new Activity(2l, 1l, 2l, Instant.parse("2023-08-02T00:00:00Z"), Instant.parse("2023-08-01T00:00:00Z"), Instant.parse("2023-08-01T00:00:00Z"), 300d, Boolean.FALSE);
        bookingItems = new ArrayList<>();
        bookingItems.add(new BookingItemRequest(BookedItem.ACTIVITY, 1l));
        bookingItems.add(new BookingItemRequest(BookedItem.ACTIVITY, 2l));
    }

    @Test
    void testComputeTotalPrice_PositiveCase() {
        // Arrange
        when(activityRepository.findById(1l)).thenReturn(Optional.ofNullable(activity1));
        when(activityRepository.findById(2l)).thenReturn(Optional.ofNullable(activity2));

        // Act
        Double totalPrice = bookingLineItemService.computeTotalPrice(bookingItems);

        // Assert
        assertEquals(400d, totalPrice);
    }
}