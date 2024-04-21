package com.group15.tourassist.service.impl;

import com.group15.tourassist.entity.ResortMaster;

import java.util.List;

public interface IResortMasterService {
    List<ResortMaster> getAllResortsByDestinationId(Long destinationId);
}
