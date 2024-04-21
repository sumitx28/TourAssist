package com.group15.tourassist.service;

import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.Guest;
import com.group15.tourassist.repository.IGuestRepository;
import com.group15.tourassist.service.impl.IGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService implements IGuestService {

    @Autowired
    private IGuestRepository guestRepository;

    /**
     * @param guests List of guests for a booking
     * @param booking corresponding booking object
     */
    @Override
    public void createGuests(List<Guest> guests, Booking booking) {
        for(Guest guest : guests) {
            guest.setBooking(booking);
            guestRepository.save(guest);
        }
    }
}
