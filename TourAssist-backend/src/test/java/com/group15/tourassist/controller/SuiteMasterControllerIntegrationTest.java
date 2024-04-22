package com.group15.tourassist.controller;

import com.group15.tourassist.entity.SuiteMaster;
import com.group15.tourassist.entity.TravelModeMaster;
import com.group15.tourassist.repository.ISuiteMasterRepository;
import com.group15.tourassist.service.SuiteMasterService;
import com.group15.tourassist.web.controller.SuiteMasterController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
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

    @Test
    void testSuitesMasterController() {
        // Arrange
        int numberOfSuites = 8;

        // Act
        ResponseEntity<List<SuiteMaster>> response = restTemplate.exchange("/api/v1/suites", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SuiteMaster>>() {});

        // Assert
        Assert.assertEquals(numberOfSuites, response.getBody().size());
    }

}