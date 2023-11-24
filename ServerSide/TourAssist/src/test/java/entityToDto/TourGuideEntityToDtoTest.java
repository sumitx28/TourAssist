package entityToDto;

import com.group15.tourassist.dto.GuideDTO;
import com.group15.tourassist.dto.TourGuideDTO;
import com.group15.tourassist.entity.DestinationMaster;
import com.group15.tourassist.entity.GuideMaster;
import com.group15.tourassist.entity.TourGuide;
import com.group15.tourassist.entityToDto.TourGuideEntityToDto;
import com.group15.tourassist.repository.IGuideMasterRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class TourGuideEntityToDtoTest {

    @Test
    void testTourGuideEntityToDto() {
        TourGuide tourGuide = mock(TourGuide.class);
        when(tourGuide.getId()).thenReturn(1L);
        when(tourGuide.getPackageId()).thenReturn(2L);
        when(tourGuide.getGuideMasterId()).thenReturn(3L);
        when(tourGuide.getPriceStartDate()).thenReturn(Instant.parse("2023-01-01T00:00:00Z"));
        when(tourGuide.getPriceExpiryDate()).thenReturn(Instant.parse("2023-01-10T00:00:00Z"));
        when(tourGuide.getPrice()).thenReturn(100.0);
        when(tourGuide.getIsCustomizable()).thenReturn(true);

        GuideMaster guideMaster = GuideMaster.builder()
                .id(3L)
                .guideName("TestGuide")
                .experienceYears("5")
                .destinationMaster(DestinationMaster.builder().id(4L).city("TestCity").country("TestCountry").build())
                .build();

        IGuideMasterRepository guideMasterRepository = mock(IGuideMasterRepository.class);
        when(guideMasterRepository.findById(3L)).thenReturn(Optional.of(guideMaster));

        GuideDTO guideDTO = GuideDTO.builder()
                .guideId(3L)
                .guideName("TestGuide")
                .build();

//        GuideMasterEntityToDto guideMasterEntityToDto = mock(GuideMasterEntityToDto.class);
//        when(guideMasterEntityToDto.guideMasterEntityToDto(guideMaster)).thenReturn(guideMasterDTO);

        TourGuideEntityToDto converter = new TourGuideEntityToDto();
        converter.guideMasterRepository = guideMasterRepository;

        TourGuideDTO resultDto = converter.tourGuideEntityToDto(tourGuide);

        assertEquals(1L, resultDto.getId());
        assertEquals(2L, resultDto.getPackageId());
        assertEquals(Instant.parse("2023-01-01T00:00:00Z"), resultDto.getPriceStartDate());
        assertEquals(Instant.parse("2023-01-10T00:00:00Z"), resultDto.getPriceExpiryDate());
        assertEquals(true, resultDto.getIsCustomizable());

        verify(guideMasterRepository, times(1)).findById(3L);
    }
}

