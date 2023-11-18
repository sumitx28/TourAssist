package com.group15.tourassist.web.controller;

import com.group15.tourassist.request.CustomerSearchPackageRequest;
import com.group15.tourassist.response.SearchPackagesWebResponse;
import com.group15.tourassist.service.ISearchPackageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = {
                "*",
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })
@RestController
@RequestMapping("/api/v1/search/")
@RequiredArgsConstructor
public class SearchPackageController {

    private final ISearchPackageService searchPackageService;
    Logger log = LoggerFactory.getLogger(SearchPackageController.class);

    @PostMapping("/travel-packages")
    public ResponseEntity<SearchPackagesWebResponse> searchTravelPackages(@RequestParam(required = false) String sortBy, @RequestParam(required = false) String filterBy, @Validated @RequestBody CustomerSearchPackageRequest customerSearchPackageRequest) {
        log.info("** inside searchTravelPackages customerSearchPackageRequest: {}", customerSearchPackageRequest);
        log.info("sortBy: {}, and filterBy: {}", sortBy, filterBy);
        SearchPackagesWebResponse searchPackagesWebResponse = searchPackageService.getSearchTravelPackages(customerSearchPackageRequest, sortBy,filterBy);

        return ResponseEntity.ok(searchPackagesWebResponse);
    }

}
