package com.group15.tourassist.web.controller;

import com.group15.tourassist.request.PackageCreateRequest;
import com.group15.tourassist.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class PackageController {

    @PostMapping("/create-package")
    private ResponseEntity<AuthenticationResponse> registerAgent(@RequestBody PackageCreateRequest request) {

        // TODO: Implement CRUD for create package.
        return null;
    }
}
