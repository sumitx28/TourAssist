package com.group15.tourassist.service;

import com.group15.tourassist.entity.ActivityMaster;
import com.group15.tourassist.repository.IActivityMasterRepository;
import com.group15.tourassist.service.impl.IActivityMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityMasterService implements IActivityMasterService {

    @Autowired
    private IActivityMasterRepository activityMasterRepository;

    @Override
    public List<ActivityMaster> findAllActivities() {
        return activityMasterRepository.findAll();
    }
}
