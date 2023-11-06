package com.group15.tourassist.service;

import com.group15.tourassist.entity.PackageMedia;
import com.group15.tourassist.repository.IPackageMediaRepository;
import com.group15.tourassist.request.PackageMediaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;

@Service
public class PackageMediaService implements IPackageMediaService {

    @Autowired
    private IPackageMediaRepository packageMediaRepository;

    @Override
    public void saveAllPackageMedia(List<PackageMediaRequest> requests, Long packageId) {
        for(PackageMediaRequest request : requests) {
            PackageMedia packageMedia = createPackageMedia(request, packageId);
            packageMediaRepository.save(packageMedia);
        }
    }


    /**
     * @param request request object
     * @param packageId package_id
     * @return PackageMedia object
     */
    private PackageMedia createPackageMedia(PackageMediaRequest request, Long packageId) {
        return PackageMedia.builder()
                .description(request.getDescription())
                .uploadDate(Instant.now())
                .packageId(packageId)
                .media(request.getImage())
                .build();
    }

}
