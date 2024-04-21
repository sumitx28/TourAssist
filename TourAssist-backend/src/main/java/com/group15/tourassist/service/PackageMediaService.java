package com.group15.tourassist.service;

import com.group15.tourassist.entity.PackageMedia;
import com.group15.tourassist.repository.IPackageMediaRepository;
import com.group15.tourassist.service.impl.IPackageMediaService;
import com.group15.tourassist.service.impl.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class PackageMediaService implements IPackageMediaService {

    @Autowired
    private IPackageMediaRepository packageMediaRepository;

    @Autowired
    private IStorageService storageService;

    @Override
    public void saveAllPackageMedia(List<MultipartFile> images, Long packageId) throws IOException {
        // store images on cloud
        List<String> paths = storageService.uploadImages(images);

        // store path in database
        for(String path : paths) {
            PackageMedia packageMedia = createPackageMedia(path, packageId);
            packageMediaRepository.save(packageMedia);
        }
    }


    /**
     * @param path cloud path
     * @param packageId package_id
     * @return PackageMedia object
     */
    private PackageMedia createPackageMedia(String path, Long packageId) {
        return PackageMedia.builder()
                .description("")
                .uploadDate(Instant.now())
                .packageId(packageId)
                .media(path)
                .build();
    }

}
