package com.group15.tourassist.controller;

import com.group15.tourassist.entity.SuiteMaster;
import com.group15.tourassist.entity.TravelModeMaster;
import com.group15.tourassist.repository.ISuiteMasterRepository;
import com.group15.tourassist.service.SuiteMasterService;
import com.group15.tourassist.web.controller.SuiteMasterController;
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
class SuiteMasterControllerIntegrationTest {

    @InjectMocks
    SuiteMasterController suiteMasterController;

    @Mock
    SuiteMasterService suiteMasterService;

    @Autowired
    ISuiteMasterRepository suiteMasterRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    // integration test for /api/v1/suites GET end-point
    @Test
    void testSuitesMasterController() {

        // Arrange
        List<SuiteMaster> suiteMasters = new ArrayList<>();
        suiteMasters.add(new SuiteMaster(1L, "Delux Room"));
        suiteMasters.add(new SuiteMaster(2L, "Standard Room"));
        suiteMasterRepository.saveAll(suiteMasters);

        // Act
        ResponseEntity<List<TravelModeMaster>> response = restTemplate.exchange("/api/v1/suites", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TravelModeMaster>>() {});


        // Assert
        Assert.assertEquals(2, response.getBody().size());
    }

}