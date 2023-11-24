package entityToDto;

import com.group15.tourassist.dto.DestinationMasterDTO;
import com.group15.tourassist.entity.DestinationMaster;
import com.group15.tourassist.entityToDto.DestinationMasterEntityToDto;
import com.group15.tourassist.repository.IGuideMasterRepository;
import com.group15.tourassist.repository.IResortMasterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;

import com.group15.tourassist.entity.ResortMaster;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DestinationMasterEntityToDtoTest {

@Mock
private DestinationMasterEntityToDto destinationMasterEntityToDto;

    @Mock
    private IGuideMasterRepository guideMasterRepository;

    @Mock
    private IResortMasterRepository resortMasterRepository;

    @BeforeEach
    public void setUp() {
        destinationMasterEntityToDto = new DestinationMasterEntityToDto();
        destinationMasterEntityToDto.guideMasterRepository = guideMasterRepository;
        destinationMasterEntityToDto.resortMasterRepository = resortMasterRepository;
    }

    @Test
    public void testDestinationMasterEntityToDto() {
        DestinationMaster destinationMaster = new DestinationMaster();
        destinationMaster.setId(1L);
        destinationMaster.setCity("Test City");
        destinationMaster.setCountry("Test Country");

        when(resortMasterRepository.findAllByDestinationMaster_Id(1L))
                .thenReturn(createMockResorts());
        destinationMaster.setResorts(resortMasterRepository.findAllByDestinationMaster_Id(1L));
        DestinationMasterDTO destinationMasterDTO = destinationMasterEntityToDto.destinationMasterEntityToDto(destinationMaster);

        assertEquals(1L, destinationMasterDTO.getId());
        assertEquals("Test City", destinationMasterDTO.getCity());
        assertEquals("Test Country", destinationMasterDTO.getCountry());
        assertEquals(createMockResorts(),destinationMaster.getResorts());
    }

    private List<ResortMaster> createMockResorts() {
        ResortMaster resort1 = ResortMaster.builder()
                .id(1L)
                .resortName("Mock Resort 1")
                .destinationMaster(createMockDestination())
                .build();

        ResortMaster resort2 = ResortMaster.builder()
                .id(2L)
                .resortName("Mock Resort 2")
                .destinationMaster(createMockDestination())
                .build();

        return Arrays.asList(resort1, resort2);
    }

    private DestinationMaster createMockDestination() {
        DestinationMaster destinationMaster = new DestinationMaster();
        destinationMaster.setId(1L);
        destinationMaster.setCity("Test City");
        destinationMaster.setCountry("Test Country");
        return destinationMaster;
    }
}
