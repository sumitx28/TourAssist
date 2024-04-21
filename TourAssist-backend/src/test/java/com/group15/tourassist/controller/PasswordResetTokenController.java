package com.group15.tourassist.controller;

import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.repository.PasswordResetTokenRepository;
import com.group15.tourassist.request.ForgotPasswordEmailRequest;
import com.group15.tourassist.request.PasswordResetRequest;
import com.group15.tourassist.service.EmailService;
import com.group15.tourassist.service.impl.IPasswordResetTokenService;
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
    public void testResetPasswordValidToken() {
        PasswordResetRequest request = new PasswordResetRequest("validToken", "newPassword");

        when(passwordResetTokenService.validateToken(request.getToken())).thenReturn(true);

        ResponseEntity<String> response = passwordResetTokenController.resetPassword(request);

        verify(passwordResetTokenService, times(1)).resetPassword(request.getToken(), request.getPassword());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password reset successfully.", response.getBody());
    }

    @Test
    public void testResetPasswordInvalidToken() {
        PasswordResetRequest request = new PasswordResetRequest("invalidToken", "newPassword");

        when(passwordResetTokenService.validateToken(request.getToken())).thenReturn(false);

        ResponseEntity<String> response = passwordResetTokenController.resetPassword(request);

        verify(passwordResetTokenService, never()).resetPassword(any(), any());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid or expired token.", response.getBody());
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