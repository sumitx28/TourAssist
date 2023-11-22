package com.group15.tourassist.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.group15.tourassist.service.BookingLineItemService;
import com.group15.tourassist.web.controller.BookingLineItemController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class BookingLineItemControllerTest {

    @Mock
    private BookingLineItemService bookingLineItemService;

    @InjectMocks
    private BookingLineItemController bookingLineItemController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTotalRevenueGenerated() {
        String type = "last_month";
        Long agentId = 1L;
        Double expectedRevenue = 500.0;
        when(bookingLineItemService.totalBookingPriceByType(agentId, type)).thenReturn(expectedRevenue);
        ResponseEntity<Double> responseEntity = bookingLineItemController.totalRevenueGenerated(type, agentId);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(expectedRevenue, responseEntity.getBody(), 0.001);
        verify(bookingLineItemService).totalBookingPriceByType(agentId, type);
    }
}
