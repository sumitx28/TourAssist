package entityToDto;

import com.group15.tourassist.dto.GuideDTO;
import com.group15.tourassist.dto.TourGuideDTO;
import com.group15.tourassist.entity.DestinationMaster;
import com.group15.tourassist.entity.GuideMaster;
import com.group15.tourassist.entity.TourGuide;
import com.group15.tourassist.entityToDto.TourGuideEntityToDto;
import com.group15.tourassist.repository.IGuideMasterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TourGuideEntityToDtoTest {

    @Mock
    private IGuideMasterRepository guideMasterRepository;

    @Spy
    @InjectMocks
    private TourGuideEntityToDto converter;

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

        when(guideMasterRepository.findById(3L)).thenReturn(Optional.of(guideMaster));

        TourGuideDTO resultDto = converter.tourGuideEntityToDto(tourGuide);

        assertEquals(1L, resultDto.getId());
        assertEquals(2L, resultDto.getPackageId());
        assertEquals(Instant.parse("2023-01-01T00:00:00Z"), resultDto.getPriceStartDate());
        assertEquals(Instant.parse("2023-01-10T00:00:00Z"), resultDto.getPriceExpiryDate());
        assertEquals(true, resultDto.getIsCustomizable());

        verify(guideMasterRepository, times(1)).findById(3L);
    }
}
