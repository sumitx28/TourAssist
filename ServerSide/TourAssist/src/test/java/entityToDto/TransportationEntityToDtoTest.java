package entityToDto;

import com.group15.tourassist.dto.TransportationDTO;
import com.group15.tourassist.entity.Transportation;
import com.group15.tourassist.entity.TravelModeMaster;
import com.group15.tourassist.entityToDto.TransportationEntityToDto;
import com.group15.tourassist.repository.ITravelModeMasterRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransportationEntityToDtoTest {

    @Test
    void testTransportationEntityToDto() {
        Transportation transportation = mock(Transportation.class);
        when(transportation.getId()).thenReturn(1L);
        when(transportation.getPackageId()).thenReturn(2L);
        when(transportation.getModeMasterId()).thenReturn(3L);
        when(transportation.getPrice()).thenReturn(100.0);
        when(transportation.getIsCustomizable()).thenReturn(true);

        TravelModeMaster travelModeMaster = TravelModeMaster.builder()
                .id(3L)
                .mode("TestMode")
                .build();

        ITravelModeMasterRepository travelModeMasterRepository = mock(ITravelModeMasterRepository.class);
        when(travelModeMasterRepository.findById(3L)).thenReturn(Optional.of(travelModeMaster));

        TransportationDTO transportationDTO = TransportationDTO.builder()
                .id(1L)
                .packageId(2L)
                .mode("TestMode")
                .price(100.0)
                .isCustomizable(true)
                .build();

        TransportationEntityToDto converter = new TransportationEntityToDto();
        converter.travelModeMasterRepository = travelModeMasterRepository;

        TransportationDTO resultDto = converter.transportationEntityToDto(transportation);

        assertEquals(1L, resultDto.getId());
        assertEquals(2L, resultDto.getPackageId());
        assertEquals("TestMode", resultDto.getMode());
        assertEquals(100.0, resultDto.getPrice());
        assertEquals(true, resultDto.getIsCustomizable());
    }
}

