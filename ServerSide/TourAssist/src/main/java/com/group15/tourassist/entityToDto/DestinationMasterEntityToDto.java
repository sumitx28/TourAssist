package com.group15.tourassist.entityToDto;

import com.group15.tourassist.dto.DestinationMasterDTO;
import com.group15.tourassist.entity.DestinationMaster;
import com.group15.tourassist.repository.IDestinationMasterRepository;
import com.group15.tourassist.repository.IGuideMasterRepository;
import com.group15.tourassist.repository.IResortMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DestinationMasterEntityToDto {

    @Autowired
    private IGuideMasterRepository guideMasterRepository;

    @Autowired
    private IResortMasterRepository resortMasterRepository;


    public DestinationMasterDTO destinationMasterEntityToDto(DestinationMaster destinationMaster){
        DestinationMasterDTO destinationMasterDTO = new DestinationMasterDTO();
        destinationMasterDTO.setId(destinationMaster.getId());
        destinationMasterDTO.setCity(destinationMaster.getCity());
        destinationMasterDTO.setCountry(destinationMaster.getCountry());
        destinationMasterDTO.setResorts(resortMasterRepository.findAllByDestinationMaster_Id(destinationMaster.getId()));
        // destinationMasterDTO.setGuides(guideMasterRepository.findAllByDestinationMaster_Id(destinationMaster.getId()));
        return destinationMasterDTO;
    }
}
