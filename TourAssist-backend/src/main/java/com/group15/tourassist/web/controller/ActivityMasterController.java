package com.group15.tourassist.web.controller;

import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.entity.ActivityMaster;
import com.group15.tourassist.response.ActivitiesResponse;
import com.group15.tourassist.service.impl.IActivityMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ActivityMasterController {

    Logger log = LoggerFactory.getLogger(ActivityMasterController.class);

    @Autowired
    private IActivityMasterService activityMasterService;

    @GetMapping("/activities")
    private ResponseEntity<ActivitiesResponse> getAllActivities() {
        log.info("Received request to get all activities");
        ActivitiesResponse response = new ActivitiesResponse(activityMasterService.findAllActivities());
        response.setMessage(ConstantUtils.SUCCESS);
        response.setStatusCode("200");
        log.info("The request to retrieve activities was handled.");
        return ResponseEntity.ok(response);
    }
}
