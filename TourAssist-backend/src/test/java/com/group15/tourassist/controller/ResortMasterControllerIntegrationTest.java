package com.group15.tourassist.controller;

import com.group15.tourassist.entity.*;
import com.group15.tourassist.repository.*;
import com.group15.tourassist.service.ResortMasterService;
import com.group15.tourassist.web.controller.ResortMasterController;
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
class ResortMasterControllerIntegrationTest {
    @InjectMocks
    ResortMasterController resortMasterController;

    @Mock
    ResortMasterService resortMasterService;

    @Autowired
    IResortMasterRepository resortMasterRepository;

    @Autowired
    IDestinationMasterRepository destinationMasterRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    // integration test for /api/v1/suites GET end-point
    @Test
    void testResortMasterApi() {

        // Arrange
        int numberOfResorts = 7;

        // Act
        ResponseEntity<List<ResortMaster>> response = restTemplate.exchange("/api/v1/resorts/1", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ResortMaster>>() {});


        // Assert
        Assert.assertEquals(numberOfResorts, response.getBody().size());
    }
}