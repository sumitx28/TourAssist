package com.group15.tourassist.service;

import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.dto.SearchTravelPackagesDTO;
import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.repository.IPackageRepository;
import com.group15.tourassist.repository.IPackageReviewRepository;
import com.group15.tourassist.request.CustomerSearchPackageRequest;
import com.group15.tourassist.response.SearchPackagesWebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchPackageService implements ISearchPackageService {

    private final IPackageRepository packageRepository;
    private final IAgentService agentService;
    private final IPackageReviewRepository packageReviewRepository;
    private final ITotalPackagePriceCalculatorService totalPackagePriceCalculatorService;
    Logger log = LoggerFactory.getLogger(SearchPackageService.class);

    @Autowired
    public SearchPackageService(IPackageRepository packageRepository, IAgentService agentService, IPackageReviewRepository packageReviewRepository, ITotalPackagePriceCalculatorService totalPackagePriceCalculatorService) {
        this.packageRepository = packageRepository;
        this.agentService = agentService;
        this.packageReviewRepository = packageReviewRepository;
        this.totalPackagePriceCalculatorService = totalPackagePriceCalculatorService;
    }

    @Override
    public List<Package> getPackagesDetails(CustomerSearchPackageRequest customerSearchPackageRequest) {
        String sourceCity = customerSearchPackageRequest.getSourceCity();
        String destinationCity = customerSearchPackageRequest.getDestinationCity();
        Instant startDate = customerSearchPackageRequest.getPackageStartDate();
        Instant endDate = customerSearchPackageRequest.getPackageEndDate();
        log.info("customerSearchPackageRequest: " + customerSearchPackageRequest);
        log.info("sourceCity: " + sourceCity);
        log.info("destinationCity: " + destinationCity);
        log.info("startDate: " + startDate);
        log.info("endDate: " + endDate);

        return packageRepository.getPackagesForDateRange(sourceCity, destinationCity, startDate, endDate);
    }

    /**
     * @param customerSearchPackageRequest
     * @return the list of packages as per user criteria specified.
     */
    @Override
    public SearchPackagesWebResponse getSearchTravelPackages(CustomerSearchPackageRequest customerSearchPackageRequest) {
        SearchPackagesWebResponse searchPackagesWebResponse = new SearchPackagesWebResponse();
        List<SearchTravelPackagesDTO> searchTravelPackages = new ArrayList<>();
        // get travel packages
        List<Package> packageList = getPackagesDetails(customerSearchPackageRequest);
        for (Package travelPackage : packageList) {
            populateSearchTravelPackageResponse(searchTravelPackages, travelPackage);

        }
        // now add the packages to the web response
        log.info("searchTravelPackages list: {}", searchTravelPackages);
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
        AgentDetailsDTO agentDetailsDTO = agentService. populateAgentDetails(agent);

        searchTravelPackagesDTO.setPackageId(travelPackage.getId());
        searchTravelPackagesDTO.setPackageName(travelPackage.getPackageName());
        searchTravelPackagesDTO.setPackageCreatedDate(travelPackage.getPackageCreatedDate());

        searchTravelPackagesDTO.setTotalPackagePrice(totalPackagePriceCalculatorService.getTotalPackagePrice(travelPackage.getId()));
        searchTravelPackagesDTO.setPackageReview(packageReviewRepository.getPackageReviewByPackageId(travelPackage.getId()));

        searchTravelPackagesDTO.setAgentDetails(agentDetailsDTO);
        searchTravelPackagesDTO.setIsPackageCustomizable(travelPackage.getIsCustomizable());
        searchTravelPackagesDTO.setTripStartDate(travelPackage.getTripStartDate());
        searchTravelPackagesDTO.setTripEndDate(travelPackage.getTripEndDate());

        searchTravelPackages.add(searchTravelPackagesDTO);
    }

}
