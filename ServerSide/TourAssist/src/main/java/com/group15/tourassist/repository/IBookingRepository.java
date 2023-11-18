package com.group15.tourassist.repository;

import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.time.Instant;
import java.sql.Date;
=======
>>>>>>> 5975b22d11af56ce3683595cdb5e0050a1cd2f5e
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

<<<<<<< HEAD
    @Query(value="SELECT * FROM booking b WHERE b.booking_date < :date AND agent_id = :agentId",nativeQuery = true)
    List<Booking> findAllByPastBookingDates(@Param("date") Date date, @Param("agentId") Long agentId);

    @Query(value="SELECT * FROM booking b WHERE b.booking_date > :date AND agent_id = :agentId", nativeQuery = true)
    List<Booking> findAllByUpcomingBookingDates(@Param("date") Date date, @Param("agentId") Long agentId);
=======
    @Query("SELECT b FROM Booking b WHERE b.customerId = :customerId")
    List<Booking> getBookingsByCustomerId(Long customerId);

>>>>>>> 5975b22d11af56ce3683595cdb5e0050a1cd2f5e
}

//    @Query(value = "SELECT booking SET booking_status = :bookingStatus WHERE id = :bookingId", nativeQuery = true)
//    void getPastBookings(Long bookingId, String bookingStatus);
//}
