package com.group15.tourassist.service;

import com.group15.tourassist.entity.PackageMedia;
import com.group15.tourassist.request.PackageMediaRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPackageMediaService {
    void saveAllPackageMedia(List<MultipartFile> images, Long packageId);
}
