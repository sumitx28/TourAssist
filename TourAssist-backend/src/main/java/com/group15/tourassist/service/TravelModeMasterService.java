package com.group15.tourassist.service;

import com.group15.tourassist.entity.TravelModeMaster;
import com.group15.tourassist.repository.ITravelModeMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelModeMasterService implements ITravelModeMasterService {

    @Autowired
    ITravelModeMasterRepository travelModeMasterRepository;

    // Returns all travel modes.
    @Override
    public List<TravelModeMaster> getAllTravelModes() {
        return  travelModeMasterRepository.findAll();
    }
}
