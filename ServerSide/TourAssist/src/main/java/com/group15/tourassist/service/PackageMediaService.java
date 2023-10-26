package com.group15.tourassist.service;

import com.group15.tourassist.entity.PackageMedia;
import com.group15.tourassist.repository.IPackageMediaRepository;
import com.group15.tourassist.request.PackageMediaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.List;

@Service
public class PackageMediaService implements IPackageMediaService {

    @Value("${package.media.directory}")
    private String uploadDirectory;

    @Autowired
    private IPackageMediaRepository packageMediaRepository;

    @Override
    public void saveAllPackageMedia(List<PackageMediaRequest> requests, Long packageId) {
        for(PackageMediaRequest request : requests) {
            PackageMedia packageMedia = createPackageMedia(request, packageId);
            packageMedia = packageMediaRepository.save(packageMedia);
            packageMedia.setMediaPath(uploadDirectory + "/" + packageMedia.getId() + ".jpg");
            packageMediaRepository.save(packageMedia);

            saveImageFile(request.getImage(), packageMedia.getMediaPath());
        }
    }

    private PackageMedia createPackageMedia(PackageMediaRequest request, Long packageId) {
        return PackageMedia.builder()
                .description(request.getDescription())
                .uploadDate(Instant.now())
                .packageId(packageId)
                .build();
    }

    private void saveImageFile(byte[] file, String mediaPath) {
        Path path = Paths.get(mediaPath);
        try {
            Files.write(path, file, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
