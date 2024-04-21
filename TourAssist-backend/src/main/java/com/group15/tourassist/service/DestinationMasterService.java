package com.group15.tourassist.service;

import com.group15.tourassist.entity.DestinationMaster;
import com.group15.tourassist.repository.IDestinationMasterRepository;
import com.group15.tourassist.service.impl.IDestinationMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationMasterService implements IDestinationMasterService {

    @Autowired
    IDestinationMasterRepository destinationMasterRepository;

    // Returns all locations which acts as source of truth for location dropdowns.
    @Override
    public List<DestinationMaster> getAllLocations() {
        return  destinationMasterRepository.findAll();
    }
}
