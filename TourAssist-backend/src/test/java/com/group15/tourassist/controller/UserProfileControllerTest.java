package com.group15.tourassist.controller;

import com.group15.tourassist.request.UpdateUserProfileRequest;
import com.group15.tourassist.response.UpdateUserProfileWebResponse;
import com.group15.tourassist.service.IUserProfileService;
import com.group15.tourassist.web.controller.UserProfileController;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserProfileControllerTest {

    @Mock
    private IUserProfileService userProfileService;

    @InjectMocks
    private UserProfileController userProfileController;

    public UserProfileControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateProfileWithEmailAndMobile() {
        // Prepare test data
        UpdateUserProfileRequest request = new UpdateUserProfileRequest();
        String emailTest = "new.email@example.com";
        request.setEmail(emailTest);
        String mobileTest = "1234567890";

        request.setMobile(mobileTest);

        // Act
        doNothing().when(userProfileService).updateUserProfileEmail(request);
        doNothing().when(userProfileService).updateUserProfileMobile(request);

        ResponseEntity<UpdateUserProfileWebResponse> responseEntity = userProfileController.updateProfile(request);

        // Verify service methods were called
        verify(userProfileService, times(1)).updateUserProfileEmail(request);
        verify(userProfileService, times(1)).updateUserProfileMobile(request);


        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UpdateUserProfileWebResponse responseBody = responseEntity.getBody();
        assertEquals("success", responseBody.getStatus());
        assertEquals("User profile updated successfully", responseBody.getMessage());
        assertTrue(responseBody.isEmailUpdated());
        assertTrue(responseBody.isMobileUpdated());
    }
}
