package com.group15.tourassist.repository;

import com.group15.tourassist.entity.BookingLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingLineItemRepository extends JpaRepository<BookingLineItem, Long> {

}
