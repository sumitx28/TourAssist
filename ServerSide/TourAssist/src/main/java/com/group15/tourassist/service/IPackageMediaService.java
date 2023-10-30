package com.group15.tourassist.service;

import com.group15.tourassist.entity.PackageMedia;
import com.group15.tourassist.request.PackageMediaRequest;

import java.util.List;

public interface IPackageMediaService {
    void saveAllPackageMedia(List<PackageMediaRequest> requests, Long packageId);
}
