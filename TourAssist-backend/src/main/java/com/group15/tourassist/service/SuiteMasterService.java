package com.group15.tourassist.service;

import com.group15.tourassist.entity.SuiteMaster;
import com.group15.tourassist.repository.ISuiteMasterRepository;
import com.group15.tourassist.service.impl.ISuiteMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuiteMasterService implements ISuiteMasterService {

    @Autowired
    private ISuiteMasterRepository suiteMasterRepository;

    @Override
    public List<SuiteMaster> findAllSuites() {
        return suiteMasterRepository.findAll();
    }
}
