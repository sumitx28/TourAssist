package com.group15.tourassist.entityToDto;

import com.group15.tourassist.dto.StayDto;
import com.group15.tourassist.entity.Stay;
import com.group15.tourassist.repository.IActivityMasterRepository;
import com.group15.tourassist.repository.IResortMasterRepository;
import com.group15.tourassist.repository.ISuiteMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service


public class StayEntityToDto {

    @Autowired
    private IResortMasterRepository resortMasterRepository;

    @Autowired
    private ISuiteMasterRepository suiteMasterRepository;

    public StayDto stayEntityToDto(Stay stay){
        StayDto stayDto = new StayDto();
        stayDto.setId(stay.getId());
        stayDto.setPackageId(stay.getPackageId());
        stayDto.setResortMaster(resortMasterRepository.findById(stay.getResortMasterId()));
        stayDto.setSuiteMaster(suiteMasterRepository.findById(stay.getSuiteMasterId()));
        stayDto.setPriceStartDate(stay.getPriceStartDate());
        stayDto.setPriceExpiryDate(stay.getPriceExpiryDate());
        stayDto.setPrice(stay.getPrice());
        stayDto.setIsCustomizable(stay.getIsCustomizable());
        return stayDto;
    }
}
