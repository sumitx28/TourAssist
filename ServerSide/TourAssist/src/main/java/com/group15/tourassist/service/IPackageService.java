package com.group15.tourassist.service;

import com.group15.tourassist.request.PackageCreateRequest;
import com.group15.tourassist.response.PackageDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPackageService {

    Long createNewPackage(PackageCreateRequest request, List<MultipartFile> images);

    PackageDetailResponse getPackageDetailsById(Long id);
}
