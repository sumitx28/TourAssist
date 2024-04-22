package com.group15.tourassist.controller;

import com.group15.tourassist.entity.DestinationMaster;
import com.group15.tourassist.repository.IDestinationMasterRepository;
import com.group15.tourassist.service.impl.IDestinationMasterService;
import com.group15.tourassist.web.controller.DestinationMasterController;
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

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DestinationMasterControllerIntegrationTest {

    @InjectMocks
    DestinationMasterController destinationMasterController;

    @Mock
    IDestinationMasterService destinationMasterService;

    @Autowired
    IDestinationMasterRepository destinationMasterRepository;


    @Autowired
    private TestRestTemplate restTemplate;


    // integration test for /api/v1/locations GET end-point
    @Test
    void testGetAllLocations() {

        // Arrange
        int numberOfDestinations = 20;

        // Act
        ResponseEntity<List<DestinationMaster>> response = restTemplate.exchange("/api/v1/locations", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DestinationMaster>>() {});


        // Assert
        Assert.assertEquals(numberOfDestinations, response.getBody().size());

    }
}