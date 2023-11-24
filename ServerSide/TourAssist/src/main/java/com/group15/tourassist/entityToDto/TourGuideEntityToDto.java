package com.group15.tourassist.entityToDto;

import com.group15.tourassist.dto.TourGuideDTO;
import com.group15.tourassist.entity.GuideMaster;
import com.group15.tourassist.entity.TourGuide;
import com.group15.tourassist.repository.IGuideMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TourGuideEntityToDto {

    @Autowired
    private IGuideMasterRepository guideMasterRepository;

    public TourGuideDTO tourGuideEntityToDto(TourGuide tourGuide){
        TourGuideDTO tourGuideDTO = new TourGuideDTO();
        tourGuideDTO.setId(tourGuide.getId());
        tourGuideDTO.setPackageId(tourGuide.getPackageId());
        tourGuideDTO.setGuideMaster(guideMasterRepository.findById(tourGuide.getGuideMasterId()));
        tourGuideDTO.setPriceStartDate(tourGuide.getPriceStartDate());
        tourGuideDTO.setPriceExpiryDate(tourGuide.getPriceExpiryDate());
        tourGuideDTO.setPrice(tourGuide.getPrice());
        tourGuideDTO.setIsCustomizable(tourGuide.getIsCustomizable());
        return tourGuideDTO;
    }
}
