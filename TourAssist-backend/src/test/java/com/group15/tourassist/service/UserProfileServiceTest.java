package com.group15.tourassist.service;

import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.repository.ICustomerRepository;
import com.group15.tourassist.request.UpdateUserProfileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.never;

@SpringBootTest
public class UserProfileServiceTest {

    private long appUserIdForTest;
    private String mobileForTest;

    @Mock
    private IAppUserRepository appUserRepository;

    @Mock
    private ICustomerRepository customerRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private UserProfileService userProfileService;

    @BeforeEach
    public void setUp() {
        appUserIdForTest = 1L;
        mobileForTest = "1234567890";
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void updateUserProfileEmailSuccess() {
        // Arrange
        UpdateUserProfileRequest request = new UpdateUserProfileRequest();
        appUserIdForTest = 1L;
        request.setAppUserId(appUserIdForTest);
        String emailUpdateForTest = "new@example.com";
        request.setEmail(emailUpdateForTest);

        Mockito.when(logger.isInfoEnabled()).thenReturn(true);

        // Mock the behavior of the repository
        Mockito.doNothing().when(appUserRepository).updateAppUserEmailById(appUserIdForTest, emailUpdateForTest);

        // Act
        userProfileService.updateUserProfileEmail(request);

        // Assert
        Mockito.verify(logger).info("updateUserProfileRequest: {}", request);
        Mockito.verify(appUserRepository).updateAppUserEmailById(appUserIdForTest, emailUpdateForTest);
        Mockito.verify(logger).info("email updated successfully for appUser: {}", request.getAppUserId());
    }

    @Test
    public void updateUserProfileMobileSuccess() {
        // Arrange
        UpdateUserProfileRequest request = new UpdateUserProfileRequest();
        request.setAppUserId(appUserIdForTest);
        request.setMobile(mobileForTest);

        Mockito.when(logger.isInfoEnabled()).thenReturn(true);
        int numberOfTuplesUpdated = 1; // should be always 1 for our update operation
        // Mock the behavior of the repository
        Mockito.when(customerRepository.updateCustomerMobile(appUserIdForTest, mobileForTest)).thenReturn(numberOfTuplesUpdated);

        // Act
        userProfileService.updateUserProfileMobile(request);

        // Assert
        Mockito.verify(logger).info("updateUserProfileRequest: {}", request);
        Mockito.verify(customerRepository).updateCustomerMobile(appUserIdForTest, mobileForTest);
        Mockito.verify(logger).info("mobile number updated successfully for appUserId: {}", request.getAppUserId());
    }

    @Test
    public void updateUserProfileMobile_Failure() {
        // Arrange
        UpdateUserProfileRequest request = new UpdateUserProfileRequest();
        request.setAppUserId(appUserIdForTest);
        request.setMobile(mobileForTest);

        Mockito.when(logger.isInfoEnabled()).thenReturn(true);

        int numberOfTuplesUpdated = 0; // Simulate a failure by returning 0 tuples updated

        // Mock the behavior of the repository to return 0, indicating failure
        Mockito.when(customerRepository.updateCustomerMobile(appUserIdForTest, mobileForTest)).thenReturn(numberOfTuplesUpdated);

        // Act
        userProfileService.updateUserProfileMobile(request);

        // Assert
        Mockito.verify(logger).info("updateUserProfileRequest: {}", request);
        Mockito.verify(customerRepository).updateCustomerMobile(appUserIdForTest, mobileForTest);
        Mockito.verify(logger, never()).info("mobile number updated successfully for appUserId: {}", request.getAppUserId());
    }
}
