package entityToDto;

import com.group15.tourassist.dto.ActivityDTO;
import com.group15.tourassist.entity.Activity;
import com.group15.tourassist.entity.ActivityMaster;
import com.group15.tourassist.entityToDto.ActivityEntityToDto;
import com.group15.tourassist.repository.IActivityMasterRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActivityEntityToDtoTest {

    @Test
    void testActivityEntityToDto() throws NoSuchFieldException, IllegalAccessException {
        // Mocking Activity entity
        Activity activity = mock(Activity.class);
        when(activity.getId()).thenReturn(1L);
        when(activity.getPackageId()).thenReturn(3L);
        when(activity.getActivityMasterId()).thenReturn(2L);
        when(activity.getActivityDate()).thenReturn(Instant.parse("2023-01-01T00:00:00Z"));
        when(activity.getPrice()).thenReturn(50.0);
        when(activity.getIsCustomizable()).thenReturn(true);

        // Mocking ActivityMaster entity
        ActivityMaster testActivityMaster = ActivityMaster.builder().id(2L).activityName("TestActivity").build();

        IActivityMasterRepository activityMasterRepository = mock(IActivityMasterRepository.class);

        // Mocking behavior of the repository
        when(activityMasterRepository.findById(2L)).thenReturn(Optional.of(testActivityMaster));

        // Creating ActivityEntityToDto
        ActivityEntityToDto converter = new ActivityEntityToDto();
        setPrivateField(converter, "activityMasterRepository", activityMasterRepository);

        // Performing the test
        ActivityDTO resultDto = converter.activityEntityToDto(activity);

        // Assertions
        assertEquals(1L, resultDto.getId());
        assertEquals(3L, resultDto.getPackageId());
        assertEquals("TestActivity", resultDto.getActivityMaster().get().getActivityName());
        assertEquals(Instant.parse("2023-01-01T00:00:00Z"), resultDto.getActivityDate());
        assertEquals(50.0, resultDto.getPrice());
        assertEquals(true, resultDto.getIsCustomizable());

        // Verifying that findById was called on the mock repository
        verify(activityMasterRepository, times(1)).findById(2L);
    }

    private void setPrivateField(Object object, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
}
