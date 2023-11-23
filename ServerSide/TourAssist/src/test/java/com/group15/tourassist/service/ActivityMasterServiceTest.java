package com.group15.tourassist.service;

import com.group15.tourassist.repository.IActivityMasterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class ActivityMasterServiceTest {

    @InjectMocks
    ActivityMasterService activityMasterService;

    @Mock
    IActivityMasterRepository activityMasterRepository;

    @Test
    void testFindAllActivities() {
        // Arrange
        when(activityMasterRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        activityMasterService.findAllActivities();

        // Assert
        verify(activityMasterRepository, times(1)).findAll();
    }
}