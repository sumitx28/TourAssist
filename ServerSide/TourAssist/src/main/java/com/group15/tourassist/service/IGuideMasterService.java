package com.group15.tourassist.service;

import com.group15.tourassist.entity.GuideMaster;

import java.util.List;

public interface IGuideMasterService {
    List<GuideMaster> getAllGuidesByLocation(Long destinationId);
}
