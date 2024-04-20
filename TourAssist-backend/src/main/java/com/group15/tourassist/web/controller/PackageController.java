package com.group15.tourassist.web.controller;

import com.group15.tourassist.request.PackageCreateRequest;
import com.group15.tourassist.response.AuthenticationResponse;
import com.group15.tourassist.response.PackageDetailResponse;
import com.group15.tourassist.service.IPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PackageController {

    Logger log = LoggerFactory.getLogger(PackageController.class);

    @Autowired
    private IPackageService packageService;


    /**
     * @param images List of package images
     * @param request request json for other details
     * @return package id
     * @throws IOException IO exception while uploading images
     */
    @PostMapping("/create-package")
    private ResponseEntity<Long> createPackage(@RequestPart("images") List<MultipartFile> images, @RequestPart("request") PackageCreateRequest request) throws IOException {
        log.info("** get create-package request {}", request.toString());

        Long packageId = packageService.createNewPackage(request, images);
        return ResponseEntity.of(Optional.of(packageId));
    }

    @GetMapping("/package/{packageId}")
    public ResponseEntity<PackageDetailResponse> getPackageDetails(@PathVariable Long packageId) {
        log.info("** get package details");
        var  response = packageService.getPackageDetailsById(packageId);
        return ResponseEntity.of(Optional.of(response));
    }
}
