package entityToDto;

import com.group15.tourassist.dto.ActivityDTO;
import com.group15.tourassist.entity.Activity;
import com.group15.tourassist.entity.ActivityMaster;
import com.group15.tourassist.entityToDto.ActivityEntityToDto;
import com.group15.tourassist.repository.IActivityMasterRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ActivityEntityToDtoTest {

    @Test
    void testActivityEntityToDto() {
        Activity activity = mock(Activity.class);
        when(activity.getId()).thenReturn(1L);
        when(activity.getPackageId()).thenReturn(3L);
        when(activity.getActivityMasterId()).thenReturn(2L);
        when(activity.getActivityDate()).thenReturn(Instant.parse("2023-01-01T00:00:00Z"));
        when(activity.getPrice()).thenReturn(50.0);
        when(activity.getIsCustomizable()).thenReturn(true);

        ActivityMaster testActivityMaster = ActivityMaster.builder().id(2L).activityName("TestActivity").build();

        IActivityMasterRepository activityMasterRepository = mock(IActivityMasterRepository.class);
        when(activityMasterRepository.findById(2L)).thenReturn(Optional.of(testActivityMaster));

        ActivityEntityToDto converter = new ActivityEntityToDto();
        converter.activityMasterRepository = activityMasterRepository;

        ActivityDTO resultDto = converter.activityEntityToDto(activity);

        assertEquals(1L, resultDto.getId());
        assertEquals(3L, resultDto.getPackageId());
        assertEquals("TestActivity", resultDto.getActivityMaster().get().getActivityName());
        assertEquals(Instant.parse("2023-01-01T00:00:00Z"), resultDto.getActivityDate());
        assertEquals(50.0, resultDto.getPrice());
        assertEquals(true, resultDto.getIsCustomizable());

        verify(activityMasterRepository, times(1)).findById(2L);
    }
}
