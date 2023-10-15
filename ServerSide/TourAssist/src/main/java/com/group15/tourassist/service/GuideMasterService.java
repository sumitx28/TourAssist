package com.group15.tourassist.service;

import com.group15.tourassist.entity.GuideMaster;
import com.group15.tourassist.repository.IGuideMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideMasterService implements IGuideMasterService{

    @Autowired
    private IGuideMasterRepository guideMasterRepository;

    @Override
    public List<GuideMaster> getAllGuidesByLocation(Long destinationId) {
        return guideMasterRepository.findAllByDestinationMaster_Id(destinationId);
    }
}
