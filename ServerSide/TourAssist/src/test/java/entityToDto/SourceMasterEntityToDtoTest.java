package entityToDto;

import com.group15.tourassist.dto.SourceMasterDTO;
import com.group15.tourassist.entity.DestinationMaster;
import com.group15.tourassist.entityToDto.SourceMasterEntityToDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SourceMasterEntityToDtoTest {

    @Test
    void testSourceMasterEntityToDto() {
        DestinationMaster destinationMaster = mock(DestinationMaster.class);
        when(destinationMaster.getId()).thenReturn(1L);
        when(destinationMaster.getCity()).thenReturn("TestCity");
        when(destinationMaster.getCountry()).thenReturn("TestCountry");

        SourceMasterEntityToDto converter = new SourceMasterEntityToDto();
        SourceMasterDTO resultDTO = converter.sourceMasterEntityToDto(destinationMaster);

        assertEquals(1L, resultDTO.getId());
        assertEquals("TestCity", resultDTO.getCity());
        assertEquals("TestCountry", resultDTO.getCountry());
    }
}

