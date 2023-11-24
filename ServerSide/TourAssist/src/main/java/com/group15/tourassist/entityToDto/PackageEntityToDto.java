package com.group15.tourassist.entityToDto;

import com.group15.tourassist.dto.PackageDTO;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.repository.IAgentRepository;
import com.group15.tourassist.repository.IDestinationMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageEntityToDto {
    @Autowired
    public IAgentRepository agentRepository;

    @Autowired
    public IDestinationMasterRepository destinationMasterRepository;

    @Autowired
    public
    DestinationMasterEntityToDto destinationMasterEntityToDto;

    @Autowired
    private SourceMasterEntityToDto sourceMasterEntityToDto;

    @Autowired
    private AgentEntityToDto agentEntityToDto;

    public PackageDTO packageEntityToDto( Package packageDetails){
        PackageDTO packageDTO = new PackageDTO();
        packageDTO.setId(packageDetails.getId());
        packageDTO.setPackageName(packageDetails.getPackageName());
        packageDTO.setAgent(agentEntityToDto.agentEntityToDto(agentRepository.findById(packageDetails.getAgentId()).get()));
        packageDTO.setTripStartDate(packageDetails.getTripStartDate());
        packageDTO.setTripEndDate(packageDetails.getTripEndDate());
        packageDTO.setSource(sourceMasterEntityToDto.sourceMasterEntityToDto( destinationMasterRepository.findById(packageDetails.getSourceId()).get()));
        packageDTO.setDestination(destinationMasterEntityToDto.destinationMasterEntityToDto( destinationMasterRepository.findById(packageDetails.getDestinationId()).get()));
        packageDTO.setPackageCreatedDate(packageDetails.getPackageCreatedDate());
        packageDTO.setIsCustomizable(packageDetails.getIsCustomizable());
        return packageDTO;
    }
}
