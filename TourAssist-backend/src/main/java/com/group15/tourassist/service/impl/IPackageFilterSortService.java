package com.group15.tourassist.service.impl;

import com.group15.tourassist.dto.SearchTravelPackagesDTO;
import com.group15.tourassist.entity.Package;

import java.util.List;

public interface IPackageFilterSortService {

    String[] extractParameterDetails(String parameter);

    List<Package> filterSearchPackages(List<Package> packageList, String filterBy);

    List<SearchTravelPackagesDTO> filterFinalSearchTravelPackages(List<SearchTravelPackagesDTO> searchTravelPackages, String filterBy);

    List<SearchTravelPackagesDTO> sortFinalSearchTravelPackages(List<SearchTravelPackagesDTO> searchTravelPackages, String sortBy);


}
