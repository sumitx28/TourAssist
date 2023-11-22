package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookedItem;
import com.group15.tourassist.entity.Activity;
import com.group15.tourassist.entity.BookingLineItem;
import com.group15.tourassist.repository.IActivityRepository;
import com.group15.tourassist.repository.IBookingLineItemRepository;
import com.group15.tourassist.request.BookingItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        activity1 = new Activity(1L, 1L, 1L, Instant.parse("2023-08-01T00:00:00Z"), Instant.parse("2023-08-01T00:00:00Z"), Instant.parse("2023-08-01T00:00:00Z"), 100D, Boolean.TRUE);
        activity2 = new Activity(2L, 1L, 2L, Instant.parse("2023-08-02T00:00:00Z"), Instant.parse("2023-08-01T00:00:00Z"), Instant.parse("2023-08-01T00:00:00Z"), 300D, Boolean.FALSE);
        bookingItems = new ArrayList<>();
        bookingItems.add(new BookingItemRequest(BookedItem.ACTIVITY, 1L));
        bookingItems.add(new BookingItemRequest(BookedItem.ACTIVITY, 2L));
    }

    @Test
    void testComputeTotalPrice_PositiveCase() {
        // Arrange
        when(activityRepository.findById(1L)).thenReturn(Optional.ofNullable(activity1));
        when(activityRepository.findById(2L)).thenReturn(Optional.ofNullable(activity2));

        // Act
        Double totalPrice = bookingLineItemService.computeTotalPrice(bookingItems);

        // Assert
        assertEquals(400D, totalPrice);
    }

    @Test
    public void testGetBookingLineItemsByBookingId() {
        Long bookingId = 1L;
        List<BookingLineItem> expectedLineItems = Arrays.asList(new BookingLineItem(), new BookingLineItem());

        when(bookingLineItemRepository.getLineItemsByBookingId(bookingId)).thenReturn(expectedLineItems);

        List<BookingLineItem> actualLineItems = bookingLineItemService.getBookingLineItemsByBookingId(bookingId);

        assertEquals(expectedLineItems, actualLineItems);
        verify(bookingLineItemRepository).getLineItemsByBookingId(bookingId);
    }

    @Test
    public void testTotalBookingPriceByTypeLastMonth() {
        Long agentId = 1L;
        String type = "last_month";
        List<Double> prices = Arrays.asList(100.0, 200.0);

        when(bookingLineItemRepository.getBookingPliForADuration(any(), any(), eq(agentId))).thenReturn(prices);

        Double total = bookingLineItemService.totalBookingPriceByType(agentId, type);

        assertEquals(300.0, total, 0.001);
        verify(bookingLineItemRepository).getBookingPliForADuration(any(), any(), eq(agentId));
    }

    @Test
    public void testTotalBookingPriceByTypeLastYear() {
        Long agentId = 1L;
        String type = "last_year";
        List<Double> prices = Arrays.asList(150.0, 250.0);

        when(bookingLineItemRepository.getBookingPliForADuration(any(), any(), eq(agentId))).thenReturn(prices);

        Double total = bookingLineItemService.totalBookingPriceByType(agentId, type);

        assertEquals(400.0, total, 0.001);
        verify(bookingLineItemRepository).getBookingPliForADuration(any(), any(), eq(agentId));
    }

    @Test
    public void testTotalBookingPriceByTypeInvalidType() {
        Long agentId = 1L;
        String type = "invalid_type";

        Double total = bookingLineItemService.totalBookingPriceByType(agentId, type);

        assertEquals(0.0, total, 0.001);
        //verifyZeroInteractions(bookingLineItemRepository);
    }

}