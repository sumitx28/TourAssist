package entityToDto;

import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.dto.DestinationMasterDTO;
import com.group15.tourassist.dto.PackageDTO;
import com.group15.tourassist.dto.SourceMasterDTO;
import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.entity.DestinationMaster;
import com.group15.tourassist.entityToDto.AgentEntityToDto;
import com.group15.tourassist.entityToDto.DestinationMasterEntityToDto;
import com.group15.tourassist.entityToDto.PackageEntityToDto;
import com.group15.tourassist.entityToDto.SourceMasterEntityToDto;
import com.group15.tourassist.repository.IAgentRepository;
import com.group15.tourassist.repository.IDestinationMasterRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class PackageEntityToDtoTest {

    @Test
    void testPackageEntityToDto() {
        Package packageDetails = mock(Package.class);
        when(packageDetails.getId()).thenReturn(1L);
        when(packageDetails.getPackageName()).thenReturn("TestPackage");
        when(packageDetails.getAgentId()).thenReturn(2L);
        when(packageDetails.getTripStartDate()).thenReturn(Instant.parse("2023-01-01T00:00:00Z"));
        when(packageDetails.getTripEndDate()).thenReturn(Instant.parse("2023-01-10T00:00:00Z"));
        when(packageDetails.getSourceId()).thenReturn(3L);
        when(packageDetails.getDestinationId()).thenReturn(4L);
        when(packageDetails.getPackageCreatedDate()).thenReturn(Instant.parse("2022-12-01T00:00:00Z"));
        when(packageDetails.getIsCustomizable()).thenReturn(true);

        Agent testAgent = Agent.builder()
                .id(2L)
                .appUser(AppUser.builder().build())
                .companyName("TestCompany")
                .mobile("TestMobile")
                .employeeCount(10)
                .verificationId("TestVerificationId")
                .verificationDocLink("TestDocLink")
                .build();

        DestinationMasterDTO destinationMasterDTO = DestinationMasterDTO.builder()
                .id(4L)
                .city("TestDestCity")
                .country("TestDestCountry")
                .build();

        SourceMasterDTO sourceMasterDTO = SourceMasterDTO.builder()
                .id(3L)
                .city("TestSourceCity")
                .country("TestSourceCountry")
                .build();

        AgentDetailsDTO agentDTO = AgentDetailsDTO.builder()
                .agentId(2L)
                .companyName("TestCompany")
                .mobile("TestMobile")
                .build();
        DestinationMaster testSource = DestinationMaster.builder().id(3L).city("TestSourceCity").country("TestSourceCountry").build();
        DestinationMaster testDestination = DestinationMaster.builder().id(4L).city("TestDestCity").country("TestDestCountry").build();

        IAgentRepository agentRepository = mock(IAgentRepository.class);
        when(agentRepository.findById(2L)).thenReturn(Optional.of(testAgent));

        IDestinationMasterRepository destinationMasterRepository = mock(IDestinationMasterRepository.class);
        when(destinationMasterRepository.findById(3L)).thenReturn(Optional.of(testSource));
        when(destinationMasterRepository.findById(4L)).thenReturn(Optional.of(testDestination));

        DestinationMasterEntityToDto destinationMasterEntityToDto = mock(DestinationMasterEntityToDto.class);
        when(destinationMasterEntityToDto.destinationMasterEntityToDto(testSource)).thenReturn(destinationMasterDTO);

        SourceMasterEntityToDto sourceMasterEntityToDto = mock(SourceMasterEntityToDto.class);
        when(sourceMasterEntityToDto.sourceMasterEntityToDto(testDestination)).thenReturn(sourceMasterDTO);

        AgentEntityToDto agentEntityToDto = mock(AgentEntityToDto.class);
        when(agentEntityToDto.agentEntityToDto(testAgent)).thenReturn(agentDTO);

        PackageEntityToDto converter = new PackageEntityToDto();
        converter.agentRepository = agentRepository;
        converter.destinationMasterRepository = destinationMasterRepository;
        converter.destinationMasterEntityToDto = destinationMasterEntityToDto;
        converter.sourceMasterEntityToDto = sourceMasterEntityToDto;
        converter.agentEntityToDto = agentEntityToDto;

        PackageDTO resultDto = converter.packageEntityToDto(packageDetails);

        assertEquals(1L, resultDto.getId());
        assertEquals("TestPackage", resultDto.getPackageName());

        verify(agentRepository, times(1)).findById(2L);
        verify(destinationMasterRepository, times(1)).findById(3L);
        verify(destinationMasterRepository, times(1)).findById(4L);
    }
}
