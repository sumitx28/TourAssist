package com.group15.tourassist.web.controller;

import com.group15.tourassist.entity.ActivityMaster;
import com.group15.tourassist.service.IActivityMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
@RequestMapping("/api/v1")
public class ActivityMasterController {

    Logger log = LoggerFactory.getLogger(ActivityMasterController.class);

    @Autowired
    private IActivityMasterService activityMasterService;

    @GetMapping("/activities")
    private ResponseEntity<List<ActivityMaster>> getAllActivities() {
        log.info("** get activities request");
        List<ActivityMaster> activities = activityMasterService.findAllActivities();
        return ResponseEntity.of(Optional.of(activities));
    }
}
