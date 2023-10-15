package com.group15.tourassist.web.controller;

import com.group15.tourassist.entity.ActivityMaster;
import com.group15.tourassist.entity.SuiteMaster;
import com.group15.tourassist.repository.IActivityMasterRepository;
import com.group15.tourassist.service.IActivityMasterService;
import com.group15.tourassist.service.ISuiteMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
