package com.group15.tourassist.entity;

import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.request.BookingRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "agent_id")
    private Long agentId;

    @Column(name = "booking_date")
    private Instant bookingDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "booking_status")
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;


    /**
     * @param request Booking request object
     * @param totalPrice total price of this booking
     * @param status CONFIRM/PENDING
     * @return Booking object
     */
    public static Booking getBookingFromRequest(BookingRequest request, Double totalPrice, BookingStatus status) {
        return Booking.builder()
                .packageId(request.getPackageId())
                .customerId(request.getCustomerId())
                .agentId(request.getAgentId())
                .bookingDate(Instant.now())
                .totalPrice(totalPrice)
                .bookingStatus(status)
                .build();
    }
}
