package com.group15.tourassist.repository;

import com.group15.tourassist.dto.SourceDestinationDTO;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.request.CustomerSearchPackageRequest;

import java.util.List;


public interface IPackageSearchRepository {
    List<Package> getPackagesForDateRange(CustomerSearchPackageRequest customerSearchPackageRequest, SourceDestinationDTO sourceDestinationDTO);

}
