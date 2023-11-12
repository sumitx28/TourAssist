package com.group15.tourassist.entity;

import com.group15.tourassist.core.enums.BookedItem;
import com.group15.tourassist.request.BookingItemRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "booking_line_item")
public class BookingLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "item_name")
    @Enumerated(EnumType.STRING)
    private BookedItem bookedItem;

    @Column(name = "booked_item_id")
    private Long bookedItemId;

    @Column(name = "price")
    private Double price;

    /**
     * @param request Request object from FE
     * @param booking corresponding booking object
     * @param price price of the line item
     * @return BookingLineItem entity
     * SRP is followed and moved this method here in BookingLineItem class instead of keeping it in service class.
     */
    public static BookingLineItem getBookingLineItem(BookingItemRequest request, Booking booking, Double price) {
        return BookingLineItem.builder()
                .booking(booking)
                .bookedItem(request.getItemName())
                .bookedItemId(request.getItemId())
                .price(price)
                .build();
    }
}
