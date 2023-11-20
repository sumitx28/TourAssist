package com.group15.tourassist.repository;

import com.group15.tourassist.entity.BookingLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingLineItemRepository extends JpaRepository<BookingLineItem, Long> {

    @Query("SELECT bli FROM BookingLineItem bli WHERE bli.booking.id = :bookingId")
    List<BookingLineItem> getLineItemsByBookingId(Long bookingId);

}
