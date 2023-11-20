package com.group15.tourassist.repository;

import com.group15.tourassist.entity.BookingLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IBookingLineItemRepository extends JpaRepository<BookingLineItem, Long> {

    @Query("SELECT bli FROM BookingLineItem bli WHERE bli.booking.id = :bookingId")
    List<BookingLineItem> getLineItemsByBookingId(Long bookingId);

    @Query(value="SELECT bli.price FROM booking_line_item bli INNER JOIN booking b ON bli.booking_id = b.id INNER JOIN payment_transaction p ON b.id = p.booking_id WHERE p.transaction_status='SUCCESS' AND p.transaction_date> :fromDate AND p.transaction_date< :toDate AND b.agent_id= :agentId", nativeQuery=true)
    List<Double> getBookingPliForADuration(Date fromDate, Date toDate, Long agentId);
}
