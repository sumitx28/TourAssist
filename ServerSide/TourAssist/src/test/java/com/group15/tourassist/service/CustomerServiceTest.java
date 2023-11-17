package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookedItem;
import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.core.enums.Role;
import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.entity.Customer;
import com.group15.tourassist.entity.Guest;
import com.group15.tourassist.request.BookingItemRequest;
import com.group15.tourassist.request.BookingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    private AppUser appUser;
    private Customer customer;

    @BeforeEach
    public void setup() {
        appUser = new AppUser(1L, "customer@gmail.com", Role.CUSTOMER, "abcd1234", new ArrayList<>());
        customer = new Customer(1L, appUser, "Raj", "Patel", "7575645567", Instant.parse("1996-02-11T00:00:00Z"), "Canada");
    }

    @Test
    void getCustomerByAppUserId() {
        Customer actualCustomer = customerService.getCustomerByAppUserId(1L);

        assertEquals(customer, actualCustomer);
    }
}