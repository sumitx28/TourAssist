package com.group15.tourassist.web.controller;

import com.group15.tourassist.request.PackageCreateRequest;
import com.group15.tourassist.response.AuthenticationResponse;
import com.group15.tourassist.service.IPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(
        origins = {
                "http://localhost:5173",
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })


@RestController
@RequestMapping("/api/v1")
public class PackageController {

    Logger log = LoggerFactory.getLogger(PackageController.class);

    @Autowired
    private IPackageService packageService;


    @PostMapping("/create-package")
    private ResponseEntity<Long> registerAgent(@RequestBody PackageCreateRequest request) {
        log.info("** get create-package request {}", request.toString());

        Long packageId = packageService.createNewPackage(request);
        return ResponseEntity.of(Optional.of(packageId));
    }
}
