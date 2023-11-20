package com.group15.tourassist.service;

import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.Guest;

import java.util.List;

public interface IGuestService {
    void createGuests(List<Guest> guests, Booking booking);
}
