package com.group15.tourassist.entityToDto;

import com.group15.tourassist.dto.TransportationDTO;
import com.group15.tourassist.entity.Transportation;
import com.group15.tourassist.repository.IActivityMasterRepository;
import com.group15.tourassist.repository.ITravelModeMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportationEntityToDto {
    @Autowired
    private ITravelModeMasterRepository travelModeMasterRepository;

    public TransportationDTO transportationEntityToDto(Transportation transportation){
        TransportationDTO transportationDTO = new TransportationDTO();
        transportationDTO.setId(transportation.getId());
        transportationDTO.setPackageId(transportation.getPackageId());
        transportationDTO.setMode(travelModeMasterRepository.findById(transportation.getModeMasterId()).get().getMode());
        transportationDTO.setPrice(transportation.getPrice());
        transportationDTO.setIsCustomizable(transportation.getIsCustomizable());
        return transportationDTO;
    }
}
