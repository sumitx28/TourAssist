package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.Role;
import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.Customer;
import com.group15.tourassist.repository.ICustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private ICustomerRepository customerRepository;

    private AppUser appUser;
    private Customer customer;

    @BeforeEach
    public void setup() {
        appUser = new AppUser(1L, "customer@gmail.com", Role.CUSTOMER, "abcd1234", new ArrayList<>());
        customer = new Customer(1L, appUser, "Raj", "Patel", "7575645567", Instant.parse("1996-02-11T00:00:00Z"), "Canada");
    }

    @Test
    void testGetCustomerByAppUserId() {
        // Arrange
        when(customerRepository.getCustomerByAppUserId(1L)).thenReturn(Optional.ofNullable(customer));

        // Act
        Customer actualCustomer = customerService.getCustomerByAppUserId(1L);

        //Assert
        assertEquals(customer, actualCustomer);
    }
}