package entityToDto;

import com.group15.tourassist.dto.StayDto;
import com.group15.tourassist.entity.ResortMaster;
import com.group15.tourassist.entity.Stay;
import com.group15.tourassist.entity.SuiteMaster;
import com.group15.tourassist.entityToDto.StayEntityToDto;
import com.group15.tourassist.repository.IResortMasterRepository;
import com.group15.tourassist.repository.ISuiteMasterRepository;
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
class StayEntityToDtoTest {

    @Mock
    private IResortMasterRepository resortMasterRepository;

    @Mock
    private ISuiteMasterRepository suiteMasterRepository;

    @Spy
    @InjectMocks
    private StayEntityToDto converter;

    @Test
    void testStayEntityToDto() {
        Stay stay = mock(Stay.class);
        when(stay.getId()).thenReturn(1L);
        when(stay.getPackageId()).thenReturn(4L);
        when(stay.getResortMasterId()).thenReturn(2L);
        when(stay.getSuiteMasterId()).thenReturn(3L);
        when(stay.getPriceStartDate()).thenReturn(Instant.parse("2023-01-01T00:00:00Z"));
        when(stay.getPriceExpiryDate()).thenReturn(Instant.parse("2023-02-01T00:00:00Z"));
        when(stay.getPrice()).thenReturn(100.0);
        when(stay.getIsCustomizable()).thenReturn(true);

        ResortMaster testResortMaster = ResortMaster.builder().id(2L).resortName("TestResort").build();
        SuiteMaster testSuiteMaster = SuiteMaster.builder().id(3L).suiteType("TestSuite").build();

        when(resortMasterRepository.findById(2L)).thenReturn(Optional.of(testResortMaster));
        when(suiteMasterRepository.findById(3L)).thenReturn(Optional.of(testSuiteMaster));

        StayDto resultDto = converter.stayEntityToDto(stay);

        assertEquals(1L, resultDto.getId());
        assertEquals(4L, resultDto.getPackageId());
        assertEquals("TestResort", resultDto.getResortMaster().get().getResortName());
        assertEquals("TestSuite", resultDto.getSuiteMaster().get().getSuiteType());
        assertEquals(Instant.parse("2023-01-01T00:00:00Z"), resultDto.getPriceStartDate());
        assertEquals(Instant.parse("2023-02-01T00:00:00Z"), resultDto.getPriceExpiryDate());
        assertEquals(100.0, resultDto.getPrice());
        assertEquals(true, resultDto.getIsCustomizable());


        verify(resortMasterRepository, times(1)).findById(2L);
        verify(suiteMasterRepository, times(1)).findById(3L);
    }

}
