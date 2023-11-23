package com.group15.tourassist.service;

import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.Guest;
import com.group15.tourassist.repository.IGuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    @InjectMocks
    private GuestService guestService;

    @Mock
    private IGuestRepository guestRepository;

    private Guest guest;

    private Booking booking;

    @BeforeEach
    public void setup() {

        booking = Booking.builder()
                .id(5L)
                .agentId(10L)
                .customerId(20L)
                .bookingDate(Instant.now())
                .build();

        guest = Guest.builder()
                .id(10L)
                .booking(booking)
                .firstName("Raj")
                .lastName("Patel")
                .dateOfBirth(Instant.now())
                .build();

    }

    @Test
    void testCreateGuests() {
        // Arrange
        when(guestRepository.save(guest)).thenReturn(guest);

        // Act
        guestService.createGuests(Collections.singletonList(guest), booking);

        // Assert
        verify(guestRepository, times(1)).save(guest);
    }
}