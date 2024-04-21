package com.group15.tourassist.service.impl;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPackageMediaService {
    void saveAllPackageMedia(List<MultipartFile> images, Long packageId) throws IOException;
}
