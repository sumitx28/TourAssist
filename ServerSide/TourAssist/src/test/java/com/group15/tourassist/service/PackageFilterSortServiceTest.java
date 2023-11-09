package com.group15.tourassist.service;

import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.dto.SearchTravelPackagesDTO;
import com.group15.tourassist.entity.Package;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PackageFilterSortServiceTest {

    private String columnName;
    private String columnValue;
    private String sortBy;
    private String filterBy;
    private List<Package> packageList;
    private double totalPackagePriceHigher;
    private double totalPackagePriceLower;
    @InjectMocks
    private PackageFilterSortService packageFilterSortService;

    private static List<Package> getPackageList() {
        List<Package> packageList = new ArrayList<>();
        Package package1 = new Package();
        package1.setIsCustomizable(true);
        Package package2 = new Package();
        package2.setIsCustomizable(false);
        packageList.add(package1);
        packageList.add(package2);
        return packageList;
    }

    private List<SearchTravelPackagesDTO> createPackagesForSort() {
        List<SearchTravelPackagesDTO> searchTravelPackages = new ArrayList<>();

        SearchTravelPackagesDTO package1 = new SearchTravelPackagesDTO();
        package1.setTotalPackagePrice(totalPackagePriceHigher);
        double averagePackageRatingLower = 1.0;
        package1.setAveragePackageRatings(averagePackageRatingLower);

        SearchTravelPackagesDTO package2 = new SearchTravelPackagesDTO();
        package2.setTotalPackagePrice(totalPackagePriceLower);
        double averagePackageRatingHigher = 2.0;
        package2.setAveragePackageRatings(averagePackageRatingHigher);

        searchTravelPackages.add(package1);
        searchTravelPackages.add(package2);
        return searchTravelPackages;
    }

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sortBy = "column:value";
        // Arrange
        columnName = "column";
        columnValue = "value";
        filterBy = "column:value";
        totalPackagePriceLower = 50.0;
        totalPackagePriceHigher = 100.0;

        packageList = getPackageList();


    }

    @Test
    public void testExtractParameterDetailsNotNull() {
        // Arrange and Act
        String[] result = packageFilterSortService.extractParameterDetails(sortBy);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testExtractParameterDetails() {

        // Act
        String[] result = packageFilterSortService.extractParameterDetails(sortBy);

        // Assert
        assertEquals(columnName, result[0]);
        assertEquals(columnValue, result[1]);
    }

    @Test
    public void testFilterSearchPackagesByName() {
        // Arrange
        List<Package> packageList = new ArrayList<>();
        Package package1 = new Package();
        String packageNameToFilter = "Package1";
        package1.setPackageName(packageNameToFilter);

        Package package2 = new Package();
        String packageNameToBeRemoved = "Package2";

        package2.setPackageName(packageNameToBeRemoved);

        packageList.add(package1);
        packageList.add(package2);

        String filterBy = "packageName:Package1";

        // Act
        List<Package> result = packageFilterSortService.filterSearchPackages(packageList, filterBy);

        // Assert
        assertEquals(1, result.size());
        assertEquals(packageNameToFilter, result.get(0).getPackageName());
    }

    @Test
    public void testFilterSearchPackagesByCustomizableFalse() {
        String filterByFalse = "isCustomizable:0";

        List<Package> resultFalse = packageFilterSortService.filterSearchPackages(packageList, filterByFalse);
        assertEquals(1, resultFalse.size());
        assertFalse(resultFalse.get(0).getIsCustomizable());

    }

    @Test
    public void testFilterSearchPackagesByCustomizableTrue() {
        // Arrange
        String filterByTrue = "isCustomizable:1";

        // Act
        List<Package> resultTrue = packageFilterSortService.filterSearchPackages(packageList, filterByTrue);

        // Assert
        assertEquals(1, resultTrue.size());
        assertTrue(resultTrue.get(0).getIsCustomizable());
    }

    @Test
    public void testFilterFinalSearchTravelPackagesByRating() {
        // Arrange
        List<SearchTravelPackagesDTO> searchTravelPackages = new ArrayList<>();
        SearchTravelPackagesDTO package1 = new SearchTravelPackagesDTO();
        double averagePackageRatingHigher = 4.5;

        package1.setAveragePackageRatings(averagePackageRatingHigher);
        SearchTravelPackagesDTO package2 = new SearchTravelPackagesDTO();
        double averagePackageRatingsLower = 3.5;

        package2.setAveragePackageRatings(averagePackageRatingsLower);
        searchTravelPackages.add(package1);
        searchTravelPackages.add(package2);

        String filterBy = "packageRating:4";

        // Act
        List<SearchTravelPackagesDTO> result = packageFilterSortService.filterFinalSearchTravelPackages(searchTravelPackages, filterBy);

        // Assert
        assertEquals(1, result.size());
        assertEquals(averagePackageRatingHigher, result.get(0).getAveragePackageRatings(), 0.001);
    }

    @Test
    public void testSortFinalSearchTravelPackagesByPriceSortAsc() {
        // Arrange
        List<SearchTravelPackagesDTO> searchTravelPackages = createPackagesForSort();

        String sortBy = ConstantUtils.PRICE_SORT + ConstantUtils.filterSortDelimiter + ConstantUtils.SORT_ASC;

        // Act
        List<SearchTravelPackagesDTO> result = packageFilterSortService.sortFinalSearchTravelPackages(searchTravelPackages, sortBy);

        // Assert
        assertEquals(2, result.size());
        assertEquals(totalPackagePriceLower, result.get(0).getTotalPackagePrice(), 0.001);
        assertEquals(totalPackagePriceHigher, result.get(1).getTotalPackagePrice(), 0.001);
    }

    @Test
    public void testSortFinalSearchTravelPackagesPriceSortDesc() {
        // Arrange
        List<SearchTravelPackagesDTO> searchTravelPackages = createPackagesForSort();
        String sortBy = ConstantUtils.PRICE_SORT + ConstantUtils.filterSortDelimiter + ConstantUtils.SORT_DESC;

        // Act
        List<SearchTravelPackagesDTO> result = packageFilterSortService.sortFinalSearchTravelPackages(searchTravelPackages, sortBy);

        // Assert
        assertEquals(2, result.size());
        assertEquals(totalPackagePriceHigher, result.get(0).getTotalPackagePrice(), 0.001);
        assertEquals(totalPackagePriceLower, result.get(1).getTotalPackagePrice(), 0.001);
    }

    @Test
    public void testSortFinalSearchTravelPackages_RatingSortAsc() {
        // Arrange
        List<SearchTravelPackagesDTO> searchTravelPackages = createPackagesForSort();
        String sortBy = ConstantUtils.PACKAGE_RATING + ConstantUtils.filterSortDelimiter + ConstantUtils.SORT_ASC;

        // Act
        List<SearchTravelPackagesDTO> result = packageFilterSortService.sortFinalSearchTravelPackages(searchTravelPackages, sortBy);

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testSortFinalSearchTravelPackages_RatingSortDesc() {
        // Arrange
        List<SearchTravelPackagesDTO> searchTravelPackages = createPackagesForSort();
        String sortBy = ConstantUtils.PACKAGE_RATING + ConstantUtils.filterSortDelimiter + ConstantUtils.SORT_DESC;

        // Act
        List<SearchTravelPackagesDTO> result = packageFilterSortService.sortFinalSearchTravelPackages(searchTravelPackages, sortBy);

        // Assert
        assertEquals(2, result.size());
    }


    @Test
    public void testFilterFinalSearchTravelPackages_PriceRangeFilter() {
        // Arrange
        List<SearchTravelPackagesDTO> searchTravelPackages = createPackagesForSort();
        String filterBy = "price:50#99";  // Assuming a price range from 50 to 99

        // Act
        List<SearchTravelPackagesDTO> result = packageFilterSortService.filterFinalSearchTravelPackages(searchTravelPackages, filterBy);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

    }
}
