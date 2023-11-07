package com.group15.tourassist.web.controller;

import com.group15.tourassist.request.CustomerSearchPackageRequest;
import com.group15.tourassist.response.SearchPackagesWebResponse;
import com.group15.tourassist.service.ISearchPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search/")
public class SearchPackageController {

    private final ISearchPackageService searchPackageService;
    Logger log = LoggerFactory.getLogger(SearchPackageController.class);

    @Autowired
    public SearchPackageController(ISearchPackageService searchPackageService) {
        this.searchPackageService = searchPackageService;
    }

    @GetMapping("/travel-packages")
    public ResponseEntity<SearchPackagesWebResponse> searchTravelPackages(@Validated @RequestBody CustomerSearchPackageRequest customerSearchPackageRequest) {
        log.info("** inside searchTravelPackages customerSearchPackageRequest: {}", customerSearchPackageRequest);

        SearchPackagesWebResponse searchPackagesWebResponse = searchPackageService.getSearchTravelPackages(customerSearchPackageRequest);

        return ResponseEntity.ok(searchPackagesWebResponse);
    }

    @GetMapping("/travel-packages-v2")
    public ResponseEntity<String> sayHello() {
        log.info("** inside hello from secure point request");
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
