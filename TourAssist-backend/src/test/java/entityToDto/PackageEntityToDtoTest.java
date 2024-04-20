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

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PackageEntityToDtoTest {

    @Test
    void testPackageEntityToDto() throws NoSuchFieldException, IllegalAccessException {
        // Mocking Package entity
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

        // Mocking Agent entity
        Agent testAgent = Agent.builder()
                .id(2L)
                .appUser(AppUser.builder().build())
                .companyName("TestCompany")
                .mobile("TestMobile")
                .employeeCount(10)
                .verificationId("TestVerificationId")
                .verificationDocLink("TestDocLink")
                .build();

        // Mocking DestinationMasterDTO
        DestinationMasterDTO destinationMasterDTO = DestinationMasterDTO.builder()
                .id(4L)
                .city("TestDestCity")
                .country("TestDestCountry")
                .build();

        // Mocking SourceMasterDTO
        SourceMasterDTO sourceMasterDTO = SourceMasterDTO.builder()
                .id(3L)
                .city("TestSourceCity")
                .country("TestSourceCountry")
                .build();

        // Mocking AgentDetailsDTO
        AgentDetailsDTO agentDTO = AgentDetailsDTO.builder()
                .agentId(2L)
                .companyName("TestCompany")
                .mobile("TestMobile")
                .build();
        DestinationMaster testSource = DestinationMaster.builder().id(3L).city("TestSourceCity").country("TestSourceCountry").build();
        DestinationMaster testDestination = DestinationMaster.builder().id(4L).city("TestDestCity").country("TestDestCountry").build();

        // Mocking repositories
        IAgentRepository agentRepository = mock(IAgentRepository.class);
        when(agentRepository.findById(2L)).thenReturn(Optional.of(testAgent));

        IDestinationMasterRepository destinationMasterRepository = mock(IDestinationMasterRepository.class);
        when(destinationMasterRepository.findById(3L)).thenReturn(Optional.of(testSource));
        when(destinationMasterRepository.findById(4L)).thenReturn(Optional.of(testDestination));

        // Mocking DestinationMasterEntityToDto
        DestinationMasterEntityToDto destinationMasterEntityToDto = mock(DestinationMasterEntityToDto.class);
        when(destinationMasterEntityToDto.destinationMasterEntityToDto(testSource)).thenReturn(destinationMasterDTO);

        // Mocking SourceMasterEntityToDto
        SourceMasterEntityToDto sourceMasterEntityToDto = mock(SourceMasterEntityToDto.class);
        when(sourceMasterEntityToDto.sourceMasterEntityToDto(testDestination)).thenReturn(sourceMasterDTO);

        // Mocking AgentEntityToDto
        AgentEntityToDto agentEntityToDto = mock(AgentEntityToDto.class);
        when(agentEntityToDto.agentEntityToDto(testAgent)).thenReturn(agentDTO);

        // Creating PackageEntityToDto
        PackageEntityToDto converter = new PackageEntityToDto();
        setPrivateField(converter, "agentRepository", agentRepository);
        setPrivateField(converter, "destinationMasterRepository", destinationMasterRepository);
        setPrivateField(converter, "destinationMasterEntityToDto", destinationMasterEntityToDto);
        setPrivateField(converter, "sourceMasterEntityToDto", sourceMasterEntityToDto);
        setPrivateField(converter, "agentEntityToDto", agentEntityToDto);

        // Performing the test
        PackageDTO resultDto = converter.packageEntityToDto(packageDetails);

        // Assertions
        assertEquals(1L, resultDto.getId());
        assertEquals("TestPackage", resultDto.getPackageName());

        // Verifying that findById was called on the mock repositories
        verify(agentRepository, times(1)).findById(2L);
        verify(destinationMasterRepository, times(1)).findById(3L);
        verify(destinationMasterRepository, times(1)).findById(4L);
    }

    private void setPrivateField(Object object, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
}
