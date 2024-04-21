package com.group15.tourassist.controller;

import com.group15.tourassist.core.enums.Role;
import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.Customer;
import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.repository.ICustomerRepository;
import com.group15.tourassist.service.impl.ICustomerService;
import com.group15.tourassist.web.controller.CustomerController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerIntegrationTest {

    @InjectMocks
    CustomerController customerController;

    @Mock
    ICustomerService customerService;

    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IAppUserRepository appUserRepository;


    // integration test for /api/v1/customer/{appUserId} GET end-point
    @Test
    void testGetCustomerAppUserId() {

        // Arrange
        AppUser appUser = new AppUser(1L, "customer@gmail.com", Role.CUSTOMER, "abcd1234", new ArrayList<>());
        Customer customer = new Customer(1L, appUser, "Raj", "Patel", "7575645567", Instant.parse("1996-02-11T00:00:00Z"), "Canada");
        appUserRepository.save(appUser);
        customerRepository.save(customer);

        // Act
        ResponseEntity<Customer> response = restTemplate.exchange("/api/v1/customer/1", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Customer>() {});


        // Assert
        Assert.assertEquals(customer.getId(), response.getBody().getId());

    }

}