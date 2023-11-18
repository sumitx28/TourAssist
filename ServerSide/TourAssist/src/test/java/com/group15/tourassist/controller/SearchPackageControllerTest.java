package com.group15.tourassist.controller;


import com.group15.tourassist.request.CustomerSearchPackageRequest;
import com.group15.tourassist.response.SearchPackagesWebResponse;
import com.group15.tourassist.service.ISearchPackageService;
import com.group15.tourassist.web.controller.SearchPackageController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SearchPackageControllerTest {

    @Mock
    private ISearchPackageService searchPackageService;

    @InjectMocks
    private SearchPackageController searchPackageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchTravelPackages() {
        // Arrange
        String sortBy = "someSorting";
        String filterBy = "someFiltering";
        CustomerSearchPackageRequest request = new CustomerSearchPackageRequest();

        // Act
        when(searchPackageService.getSearchTravelPackages(request, sortBy, filterBy)).thenReturn(new SearchPackagesWebResponse());
        ResponseEntity<SearchPackagesWebResponse> responseEntity = searchPackageController.searchTravelPackages(sortBy, filterBy, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify service method was called
        verify(searchPackageService, times(1)).getSearchTravelPackages(request, sortBy, filterBy);
    }

}
