package com.group15.tourassist.service.impl;

import com.group15.tourassist.request.PackageCreateRequest;
import com.group15.tourassist.response.PackageDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPackageService {

    Long createNewPackage(PackageCreateRequest request, List<MultipartFile> images) throws IOException;

    PackageDetailResponse getPackageDetailsById(Long id);
}
