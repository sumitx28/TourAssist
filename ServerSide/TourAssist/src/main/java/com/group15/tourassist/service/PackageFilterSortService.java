package com.group15.tourassist.service;

import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.dto.SearchTravelPackagesDTO;
import com.group15.tourassist.entity.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

@Service
public class PackageFilterSortService implements IPackageFilterSortService {

    Logger log = LoggerFactory.getLogger(PackageFilterSortService.class);

    private String filterColumnName;
    private String filterColumnValue;

    private String sortColumnName;
    private String sortColumnValue;


    /**
     * format:--> col:val
     * format for price--> price: minPrice|maxPrice
     * extracts the column name and the value for filterBy / sortBy clause
     *
     * @param parameter
     */
    @Override
    public String[] extractParameterDetails(String parameter) {
        return parameter.split(ConstantUtils.filterSortDelimiter);
    }

    @Override
    public List<Package> filterSearchPackages(List<Package> packageList, String filterBy) {

        setFilterColumNameValueDetails(filterBy);
        if (filterColumnName.equalsIgnoreCase(ConstantUtils.PACKAGE_NAME)) {
            // filter by the package name
            packageList.removeIf(aPackage -> !aPackage.getPackageName().equalsIgnoreCase(filterColumnValue));
        }
        if (filterColumnName.equalsIgnoreCase(ConstantUtils.CUSTOMIZABLE)) {
            if (filterColumnValue.equals(ConstantUtils.CUSTOMIZABLE_TRUE)) {
                packageList.removeIf(travelPackage -> !travelPackage.getIsCustomizable());
            } else {
                packageList.removeIf(Package::getIsCustomizable);
            }
        }

        return packageList;
    }

    /**
     * @param sortBy
     */
    private void setSortColumnNameValuePair(String sortBy) {
        String[] columnValuePair = extractParameterDetails(sortBy);
        this.sortColumnName = columnValuePair[0];
        this.sortColumnValue = columnValuePair[1];
    }

    /**
     * sets the filter column name and value to the class instance
     *
     * @param filterBy clause provided by the customer
     */
    private void setFilterColumNameValueDetails(String filterBy) {
        String[] columnValuePair = extractParameterDetails(filterBy);
        this.filterColumnName = columnValuePair[0];
        this.filterColumnValue = columnValuePair[1];
    }

    /**
     * ratings filter returns packages with ratings more than the specified value
     * price range filters the packages according to min and max prce specified
     *
     * @param searchTravelPackages
     * @param filterBy
     * @return the filtered searchTravelPackages list after removal of user query price range and ratings
     */
    @Override
    public List<SearchTravelPackagesDTO> filterFinalSearchTravelPackages(List<SearchTravelPackagesDTO> searchTravelPackages, String filterBy) {
        log.info("inside filterFinalSearchTravelPackages: filterBy {}", filterBy);
        log.info("size of searchTravelPackages list: {}", searchTravelPackages.size());
        setFilterColumNameValueDetails(filterBy);

        if (filterColumnName.equalsIgnoreCase(ConstantUtils.PACKAGE_RATING)) {
            Iterator<SearchTravelPackagesDTO> iterator = searchTravelPackages.iterator();
            while (iterator.hasNext()) {
                SearchTravelPackagesDTO searchTravelPackagesDTO = iterator.next();
                Double averageRatings = searchTravelPackagesDTO.getAveragePackageRatings();
                log.info("before if averageRatings: {} , userFilterColRatingsParsed: {}", averageRatings, filterColumnValue);

                if (averageRatings < Double.parseDouble(filterColumnValue)) {
                    log.info("averageRatings: {} , userFilterColRatingsParsed: {}", averageRatings, filterColumnValue);
                    iterator.remove();
                }
            }
        }

        if (filterColumnName.equalsIgnoreCase(ConstantUtils.PRICE_RANGE)) {
            String[] minMaxPriceRange = filterColumnValue.split(ConstantUtils.priceRangeDelimiter);
            double minPrice = Double.parseDouble(minMaxPriceRange[0]);
            double maxPrice = Double.parseDouble(minMaxPriceRange[1]);

            Predicate<SearchTravelPackagesDTO> shouldRemovePackage = searchTravelPackagesDTO -> searchTravelPackagesDTO.getTotalPackagePrice() < minPrice ||
                    searchTravelPackagesDTO.getTotalPackagePrice() > maxPrice;

            searchTravelPackages.removeIf(shouldRemovePackage);

        }

        return searchTravelPackages;
    }

    /**
     * @param searchTravelPackages
     * @param sortBy               the column with key value pair on which custom sorting is to be applied
     * @return the final sorted travel packages
     */
    @Override
    public List<SearchTravelPackagesDTO> sortFinalSearchTravelPackages(List<SearchTravelPackagesDTO> searchTravelPackages, String sortBy) {
        log.info("inside sortFinalSearchTravelPackages: sortBy {}", sortBy);
        setSortColumnNameValuePair(sortBy);
        log.info("sortColName: {}, sortColValue: {}", sortColumnName, sortColumnValue);
        if (sortColumnName.equalsIgnoreCase(ConstantUtils.PRICE_SORT)) {
            if (sortColumnValue.equalsIgnoreCase(ConstantUtils.SORT_ASC)) {
                // sort list asc by price
                searchTravelPackages.sort(Comparator.comparing(SearchTravelPackagesDTO::getTotalPackagePrice));
            }
            if (sortColumnValue.equalsIgnoreCase(ConstantUtils.SORT_DESC)) {
                searchTravelPackages.sort(Comparator.comparing(SearchTravelPackagesDTO::getTotalPackagePrice).reversed());
            }
        }
        if (sortColumnName.equalsIgnoreCase(ConstantUtils.PACKAGE_RATING)) {
            if (sortColumnValue.equalsIgnoreCase(ConstantUtils.SORT_ASC)) {
                // sort list asc by package rating
                searchTravelPackages.sort(Comparator.comparing(SearchTravelPackagesDTO::getAveragePackageRatings));
            }
            if (sortColumnValue.equalsIgnoreCase(ConstantUtils.SORT_DESC)) {
                searchTravelPackages.sort(Comparator.comparing(SearchTravelPackagesDTO::getAveragePackageRatings).reversed());
            }
        }
        return searchTravelPackages;
    }
}

