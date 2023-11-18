package com.group15.tourassist.repository;

import com.group15.tourassist.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {

    /**
     * @param bookingId     bookingId to update the status
     * @param bookingStatus corresponding status
     */
    @Modifying
    @Query(value = "UPDATE booking SET booking_status = :bookingStatus WHERE id = :bookingId", nativeQuery = true)
    void updateBookingStatus(Long bookingId, String bookingStatus);

    @Query("SELECT b FROM Booking b WHERE b.customerId = :customerId")
    List<Booking> getBookingsByCustomerId(Long customerId);

}
