package com.group15.tourassist.service;

import com.group15.tourassist.request.PackageCreateRequest;

public interface IPackageService {

    Long createNewPackage(PackageCreateRequest request);
}
