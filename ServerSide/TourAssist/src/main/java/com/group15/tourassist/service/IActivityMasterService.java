package com.group15.tourassist.service;

import com.group15.tourassist.entity.ActivityMaster;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IActivityMasterService {
    List<ActivityMaster> findAllActivities();
}
