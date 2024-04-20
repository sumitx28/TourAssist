package com.group15.tourassist.entityToDto;

import com.group15.tourassist.dto.DestinationMasterDTO;
import com.group15.tourassist.dto.SourceMasterDTO;
import com.group15.tourassist.entity.DestinationMaster;
import org.springframework.stereotype.Service;

@Service
public class SourceMasterEntityToDto {
    public SourceMasterDTO sourceMasterEntityToDto(DestinationMaster destinationMaster){
        SourceMasterDTO sourceMasterDTO = new SourceMasterDTO();
        sourceMasterDTO.setId(destinationMaster.getId());
        sourceMasterDTO.setCity(destinationMaster.getCity());
        sourceMasterDTO.setCountry(destinationMaster.getCountry());

        return sourceMasterDTO;
    }

}
