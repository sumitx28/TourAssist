package com.group15.tourassist.controller;

import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.PasswordResetToken;
import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.repository.PasswordResetTokenRepository;
import com.group15.tourassist.request.ForgotPasswordEmailRequest;
import com.group15.tourassist.service.EmailService;
import com.group15.tourassist.service.IPasswordResetTokenService;
import com.group15.tourassist.web.controller.PasswordResetTokenController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PasswordResetTokenControllerTest {

    @InjectMocks
    private PasswordResetTokenController passwordResetTokenController;

    @Mock
    private IAppUserRepository appUserRepository;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private IPasswordResetTokenService passwordResetTokenService;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void resetPasswordRequest_UserFoundAndTokenGenerated_EmailSent() {
        // Arrange
        ForgotPasswordEmailRequest request = new ForgotPasswordEmailRequest("test@example.com");
        AppUser appUser = new AppUser();
        when(appUserRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(appUser));
        when(passwordResetTokenRepository.findByAppUser_email(request.getEmail())).thenReturn(null);
        when(passwordResetTokenService.generatePasswordResetToken(appUser)).thenReturn("generatedToken");

        // Act
        ResponseEntity<String> response = passwordResetTokenController.resetPasswordRequest(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Use ArgumentCaptor to capture the arguments
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

        verify(emailService, times(1)).sendEmail(emailCaptor.capture(), subjectCaptor.capture(), bodyCaptor.capture());

        // Now you can assert on the captured values
        assertEquals(null, emailCaptor.getValue());
        assertNotNull(subjectCaptor.getValue());
        assertNotNull(bodyCaptor.getValue());
    }

}