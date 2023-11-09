package com.group15.tourassist.service;

import com.group15.tourassist.entity.Package;
import com.group15.tourassist.repository.IPackageRepository;
import com.group15.tourassist.repository.IPackageReviewRepository;
import com.group15.tourassist.request.CustomerSearchPackageRequest;
import com.group15.tourassist.response.SearchPackagesWebResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SearchPackageServiceTest {

    @InjectMocks
    private SearchPackageService searchPackageService;

    @Mock
    private IPackageRepository packageRepository;
    @Mock
    private IAgentService agentService;
    @Mock
    private ITotalPackagePriceCalculatorService totalPackagePriceCalculatorService;
    @Mock
    private IPackageReviewRepository packageReviewRepository;
    @Mock
    private IPackageReviewService packageReviewService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void testGetPackagesDetails() {
        // Constants for test data
        final String sourceCity = "SourceCity";
        final String destinationCity = "DestinationCity";
        final Instant startDate = Instant.parse("2023-01-01T00:00:00Z");
        final Instant endDate = Instant.parse("2023-01-15T00:00:00Z");

        // Arrange
        CustomerSearchPackageRequest request = new CustomerSearchPackageRequest();
        request.setSourceCity(sourceCity);
        request.setDestinationCity(destinationCity);
        request.setPackageStartDate(startDate);
        request.setPackageEndDate(endDate);

        // Mock the behavior of packageRepository
        List<Package> expectedPackages = List.of(new Package(), new Package());
        when(packageRepository.getPackagesForDateRange(sourceCity, destinationCity, startDate, endDate))
                .thenReturn(expectedPackages);

        // Act
        List<Package> packages = searchPackageService.getPackagesDetails(request, null, null);

        // Assert
        final int expectedPackageCount = expectedPackages.size();
        assertEquals(expectedPackageCount, packages.size());
        // Verify that the method returned the expected number of packages
    }

    @Test
    public void testGetSearchTravelPackagesWithoutSortFilter() {
        // Arrange
        CustomerSearchPackageRequest request = new CustomerSearchPackageRequest();
        String sortBy = null;
        String filterBy = null;
        // Set request attributes

        // Mock the behavior of packageRepository
        List<Package> packageList = List.of(new Package(), new Package());
        when(searchPackageService.getPackagesDetails(request, sortBy, filterBy)).thenReturn(packageList);

        // Act
        SearchPackagesWebResponse response = searchPackageService.getSearchTravelPackages(request, sortBy, filterBy);

        // Assert
        assertEquals(2, response.getTravelPackages().size());
        // Verify that the response contains the expected number of travel packages
    }

}
