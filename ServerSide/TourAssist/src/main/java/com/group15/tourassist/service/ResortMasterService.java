package com.group15.tourassist.service;

import com.group15.tourassist.entity.ResortMaster;
import com.group15.tourassist.repository.IResortMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResortMasterService implements IResortMasterService{

    @Autowired
    private IResortMasterRepository resortMasterRepository;

    @Override
    public List<ResortMaster> getAllResortsByDestinationId(Long destinationId) {
        return resortMasterRepository.findAllByDestinationMaster_Id(destinationId);
    }
}
