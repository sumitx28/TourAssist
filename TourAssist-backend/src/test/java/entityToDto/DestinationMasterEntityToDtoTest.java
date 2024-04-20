package entityToDto;

import com.group15.tourassist.dto.DestinationMasterDTO;
import com.group15.tourassist.entity.DestinationMaster;
import com.group15.tourassist.entity.ResortMaster;
import com.group15.tourassist.entityToDto.DestinationMasterEntityToDto;
import com.group15.tourassist.repository.IGuideMasterRepository;
import com.group15.tourassist.repository.IResortMasterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DestinationMasterEntityToDtoTest {

    @Mock
    private IGuideMasterRepository guideMasterRepository;

    @Mock
    private IResortMasterRepository resortMasterRepository;

    @InjectMocks
    private DestinationMasterEntityToDto entityToDtoConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void destinationMasterEntityToDtoTest() {
        DestinationMaster destinationMaster = new DestinationMaster();
        destinationMaster.setId(1L);
        destinationMaster.setCity("City");
        destinationMaster.setCountry("Country");

        ResortMaster resortMaster = new ResortMaster();
        resortMaster.setId(1L);
        resortMaster.setResortName("Resort1");
        resortMaster.setDestinationMaster(destinationMaster);

        List<ResortMaster> resortList = new ArrayList<>();
        resortList.add(resortMaster);
        when(resortMasterRepository.findAllByDestinationMaster_Id(destinationMaster.getId())).thenReturn(resortList);

        DestinationMasterDTO destinationMasterDTO = entityToDtoConverter.destinationMasterEntityToDto(destinationMaster);

        assertEquals(destinationMaster.getId(), destinationMasterDTO.getId());
        assertEquals(destinationMaster.getCity(), destinationMasterDTO.getCity());
        assertEquals(destinationMaster.getCountry(), destinationMasterDTO.getCountry());
        assertEquals(1, destinationMasterDTO.getResorts().size());
        assertEquals(resortMaster.getResortName(), destinationMasterDTO.getResorts().get(0).getResortName());
    }
}
