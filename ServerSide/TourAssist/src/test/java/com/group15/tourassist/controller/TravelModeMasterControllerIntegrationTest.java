package com.group15.tourassist.controller;

import com.group15.tourassist.entity.TravelModeMaster;
import com.group15.tourassist.repository.ITravelModeMasterRepository;
import com.group15.tourassist.service.ITravelModeMasterService;
import com.group15.tourassist.web.controller.TravelModeMasterController;
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
class TravelModeMasterControllerIntegrationTest {

    @InjectMocks
    TravelModeMasterController travelModeMasterController;

    @Mock
    ITravelModeMasterService travelModeMasterService;

    @Autowired
    ITravelModeMasterRepository travelModeMasterRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    // integration test for /api/v1/travel-modes GET end-point
    @Test
    void testTravelModeController() {

        // Arrange
        List<TravelModeMaster> travelModeMasters = new ArrayList<>();
        travelModeMasters.add(new TravelModeMaster(1L, "Train"));
        travelModeMasters.add(new TravelModeMaster(2L, "Flight"));
        travelModeMasterRepository.saveAll(travelModeMasters);

        // Act
        ResponseEntity<List<TravelModeMaster>> response = restTemplate.exchange("/api/v1/travel-modes", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TravelModeMaster>>() {});


        // Assert
        Assert.assertEquals(2, response.getBody().size());
    }
}