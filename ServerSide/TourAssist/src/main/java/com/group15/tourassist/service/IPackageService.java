package com.group15.tourassist.service;

import com.group15.tourassist.request.PackageCreateRequest;
import com.group15.tourassist.response.PackageDetailResponse;

public interface IPackageService {

    Long createNewPackage(PackageCreateRequest request);

    PackageDetailResponse getPackageDetailsById(Long id);
}
