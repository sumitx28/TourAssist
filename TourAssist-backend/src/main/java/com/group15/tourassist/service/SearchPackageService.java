package com.group15.tourassist.service;

import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.dto.SearchTravelPackagesDTO;
import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.entity.PackageReview;
import com.group15.tourassist.repository.IPackageRepository;
import com.group15.tourassist.repository.IPackageReviewRepository;
import com.group15.tourassist.request.CustomerSearchPackageRequest;
import com.group15.tourassist.response.SearchPackagesWebResponse;
import com.group15.tourassist.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchPackageService implements ISearchPackageService {

    private final IPackageRepository packageRepository;
    private final IAgentService agentService;
    private final IPackageReviewRepository packageReviewRepository;
    private final IPackagePriceCalculatorService packagePriceCalculatorService;
    private final IPackageFilterSortService packageFilterSortService;
    private final IPackageReviewService packageReviewService;
    Logger log = LoggerFactory.getLogger(SearchPackageService.class);


    @Override
    public List<Package> getPackagesDetails(CustomerSearchPackageRequest customerSearchPackageRequest, String sortBy, String filterBy) {
        List<Package> packageList;
        String sourceCity = customerSearchPackageRequest.getSourceCity();
        String destinationCity = customerSearchPackageRequest.getDestinationCity();
        Instant startDate = customerSearchPackageRequest.getPackageStartDate();
        Instant endDate = customerSearchPackageRequest.getPackageEndDate();
        log.info("customerSearchPackageRequest: " + customerSearchPackageRequest);
        log.info("sourceCity: " + sourceCity);
        log.info("destinationCity: " + destinationCity);
        log.info("startDate: " + startDate);
        log.info("endDate: " + endDate);
        log.info("sortBy: {}", sortBy);
        log.info("filerBy: {}", filterBy);
        if (sortBy != null && sortBy.equals(ConstantUtils.PACKAGE_NAME)) {
            packageList = packageRepository.getSortedPackagesForDateRange(sourceCity, destinationCity, startDate, endDate);
        } else {
            packageList = packageRepository.getPackagesForDateRange(sourceCity, destinationCity, startDate, endDate);
        }
        if (filterBy != null) {
            // filters based on package column names
            packageList = packageFilterSortService.filterSearchPackages(packageList, filterBy);
        }
        return packageList;
    }


    /**
     * Retrieves and processes search results for travel packages based on customer search criteria.
     *
     * @param customerSearchPackageRequest The customer's search criteria.
     * @param sortBy                       The sorting parameter for the search results.
     * @param filterBy                     The filtering parameter for the search results.
     * @return The web response containing the search results.
     */
    @Override
    public SearchPackagesWebResponse getSearchTravelPackages(CustomerSearchPackageRequest customerSearchPackageRequest, String sortBy, String filterBy) {
        SearchPackagesWebResponse searchPackagesWebResponse = new SearchPackagesWebResponse();
        List<SearchTravelPackagesDTO> searchTravelPackages = new ArrayList<>();
        // get travel packages
        List<Package> packageList = getPackagesDetails(customerSearchPackageRequest, sortBy, filterBy);
        for (Package travelPackage : packageList) {
            populateSearchTravelPackageResponse(searchTravelPackages, travelPackage);
        }
        // now add the packages to the web response
        log.info("searchTravelPackages list: {}", searchTravelPackages);

        if (filterBy != null) {
            packageFilterSortService.filterFinalSearchTravelPackages(searchTravelPackages, filterBy);
        }
        if (sortBy != null && !sortBy.equalsIgnoreCase(ConstantUtils.PACKAGE_NAME)) {
            packageFilterSortService.sortFinalSearchTravelPackages(searchTravelPackages, sortBy);

        }
        searchPackagesWebResponse.setTravelPackages(searchTravelPackages);
        return searchPackagesWebResponse;
    }

    /**
     * @param searchTravelPackages list of travel packages with agent review and total price info
     * @param travelPackage        package for which the SearchTravelPackagesDTO is to be populated
     */
    private void populateSearchTravelPackageResponse(List<SearchTravelPackagesDTO> searchTravelPackages, Package travelPackage) {
        SearchTravelPackagesDTO searchTravelPackagesDTO = new SearchTravelPackagesDTO();
        Long agentId = travelPackage.getAgentId();
        Agent agent = agentService.getAgentById(agentId);
        AgentDetailsDTO agentDetailsDTO = agentService.populateAgentDetails(agent);

        searchTravelPackagesDTO.setPackageId(travelPackage.getId());
        searchTravelPackagesDTO.setPackageName(travelPackage.getPackageName());
        searchTravelPackagesDTO.setPackageCreatedDate(travelPackage.getPackageCreatedDate());

        Double totalPackagePrice = packagePriceCalculatorService.getTotalPackagePrice(travelPackage.getId());
        searchTravelPackagesDTO.setTotalPackagePrice(totalPackagePrice);
        List<PackageReview> packageReviewList = packageReviewRepository.getPackageReviewByPackageId(travelPackage.getId());

        searchTravelPackagesDTO.setPackageReview(packageReviewList);
        Double averagePackageRatings = packageReviewService.calculateAveragePackageRatings(packageReviewList);
        searchTravelPackagesDTO.setAveragePackageRatings(averagePackageRatings);

        searchTravelPackagesDTO.setAgentDetails(agentDetailsDTO);
        searchTravelPackagesDTO.setIsPackageCustomizable(travelPackage.getIsCustomizable());
        searchTravelPackagesDTO.setTripStartDate(travelPackage.getTripStartDate());
        searchTravelPackagesDTO.setTripEndDate(travelPackage.getTripEndDate());

        searchTravelPackages.add(searchTravelPackagesDTO);
    }

}
