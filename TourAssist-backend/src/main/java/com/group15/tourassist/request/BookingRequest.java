package com.group15.tourassist.request;

import com.group15.tourassist.entity.Guest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Package booking request when customer creates a booking.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingRequest {

    private Long packageId;

    private Long customerId;

    private Long agentId;

    private List<BookingItemRequest> bookingItemRequests;

    private List<Guest> guests;
}
