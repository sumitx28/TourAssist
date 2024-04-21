package com.group15.tourassist.service.impl;

import com.group15.tourassist.entity.Package;
import com.group15.tourassist.request.CustomerSearchPackageRequest;
import com.group15.tourassist.response.SearchPackagesWebResponse;

import java.util.List;

public interface ISearchPackageService {

    /**
     * @param customerSearchPackageRequest
     * @return the list of packages as per user criteria specified.
     */
    List<Package> getPackagesDetails(CustomerSearchPackageRequest customerSearchPackageRequest, String sortBy, String filterBy);

    /**
     * @param customerSearchPackageRequest request criteria of the user
     * @return search travel packages for the specified request
     */
    SearchPackagesWebResponse getSearchTravelPackages(CustomerSearchPackageRequest customerSearchPackageRequest, String sortBy, String filterBy);
}







