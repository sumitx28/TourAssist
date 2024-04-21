package com.group15.tourassist.controller;

import com.group15.tourassist.request.BookingRequest;
import com.group15.tourassist.response.BookingDetailsWebResponse;
import com.group15.tourassist.response.BookingResponse;
import com.group15.tourassist.response.CustomerDetailsBookedByAgentIDResponse;
import com.group15.tourassist.service.impl.IBookingService;
import com.group15.tourassist.web.controller.BookingController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookingControllerTest {

    @InjectMocks
    private BookingController bookingController;

    @Mock
    private IBookingService bookingService;

    @Test
    public void testCreateBooking() {
        // Arrange
        BookingRequest request = new BookingRequest();
        when(bookingService.createBooking(request)).thenReturn(1L);

        // Act
        ResponseEntity<Long> response = bookingController.createBooking(request);

        // Assert
        assertEquals(1L, response.getBody()); // Adjust this based on your expected result
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify interactions with mocks (if any)
        verify(bookingService, times(1)).createBooking(request);
    }

    @Test
    public void testGetBookingDetails() {
        // Arrange
        Long appUserId = 1L;
        BookingDetailsWebResponse detailsWebResponse = new BookingDetailsWebResponse();
        when(bookingService.getAllBookingForCustomer(appUserId)).thenReturn(detailsWebResponse);

        // Act
        ResponseEntity<BookingDetailsWebResponse> response = bookingController.getBookingDetails(appUserId);

        // Assert
        assertEquals(detailsWebResponse, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify interactions with mocks (if any)
        verify(bookingService, times(1)).getAllBookingForCustomer(appUserId);
    }

    @Test
    public void testPastBookings() {
        // Arrange
        Long agentId = 1L;
        List<BookingResponse> bookingResponses = Arrays.asList(
                new BookingResponse(),
                new BookingResponse()
        );
        when(bookingService.getPastBookings(agentId)).thenReturn(bookingResponses);

        // Act
        ResponseEntity<List<BookingResponse>> response = bookingController.pastBookings(agentId);

        // Assert
        assertEquals(bookingResponses, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify interactions with mocks (if any)
        verify(bookingService, times(1)).getPastBookings(agentId);
    }

    @Test
    public void testUpcomingBookings() {
        // Arrange
        Long agentId = 1L;
        List<BookingResponse> bookingResponses = Arrays.asList(
                new BookingResponse(),
                new BookingResponse()
        );
        when(bookingService.getUpcomingBookings(agentId)).thenReturn(bookingResponses);

        // Act
        ResponseEntity<List<BookingResponse>> response = bookingController.upcomingBookings(agentId);

        // Assert
        assertEquals(bookingResponses, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify interactions with mocks (if any)
        verify(bookingService, times(1)).getUpcomingBookings(agentId);
    }

    @Test
    public void testGetCustomersBookedByAgent() {
        // Arrange
        Long agentId = 1L;
        List<CustomerDetailsBookedByAgentIDResponse> customerResponses = Arrays.asList(
                new CustomerDetailsBookedByAgentIDResponse(),
                new CustomerDetailsBookedByAgentIDResponse()
        );
        when(bookingService.getCustomersBookedByAgentID(agentId)).thenReturn(customerResponses);

        // Act
        ResponseEntity<List<CustomerDetailsBookedByAgentIDResponse>> response = bookingController.getCustomersBookedByAgent(agentId);

        // Assert
        assertEquals(customerResponses, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify interactions with mocks (if any)
        verify(bookingService, times(1)).getCustomersBookedByAgentID(agentId);
    }

}
