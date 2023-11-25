package com.group15.tourassist.service;

import com.group15.tourassist.core.config.service.TokenGenerator;
import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.PasswordResetToken;
import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.repository.PasswordResetTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetTokenServiceTest {

    @InjectMocks
    private PasswordResetTokenService passwordResetTokenService; // Replace with the actual class containing isPasswordResetTokenExpired method

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private IAppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testGeneratePasswordResetToken() {
        // Arrange
        PasswordResetTokenRepository mockRepository = mock(PasswordResetTokenRepository.class);
        PasswordEncoder mockPasswordEncoder = mock(PasswordEncoder.class);
        IAppUserRepository mockAppUserRepository = mock(IAppUserRepository.class);

        PasswordResetTokenService passwordResetTokenService = new PasswordResetTokenService(
                mockRepository, mockPasswordEncoder, mockAppUserRepository
        );

        AppUser mockAppUser = mock(AppUser.class);

        // Act
        String token = passwordResetTokenService.generatePasswordResetToken(mockAppUser);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());

        // Verify that the token is saved in the repository
        verify(mockRepository, times(1)).save(any(PasswordResetToken.class));
    }

    @Test
    public void testIsPasswordResetTokenExpired() {
        // Create a sample PasswordResetToken
        PasswordResetTokenRepository mockRepository = mock(PasswordResetTokenRepository.class);
        PasswordEncoder mockPasswordEncoder = mock(PasswordEncoder.class);
        IAppUserRepository mockAppUserRepository = mock(IAppUserRepository.class);

        PasswordResetTokenService passwordResetTokenService = new PasswordResetTokenService(
                mockRepository,
                mockPasswordEncoder,
                mockAppUserRepository
        );

        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .id(1L)
                .token("sampleToken")
                .appUser(new AppUser())
                .expiryDate(LocalDateTime.now().plusDays(1))
                .build();

        // Mock the behavior of your repository to return the sample token when findByToken is called
        when(mockRepository.findByToken("sampleToken")).thenReturn(passwordResetToken);

        // Test the method with an unexpired token
        assertFalse(passwordResetTokenService.isPasswordResetTokenExpired("sampleToken"));

        // Test the method with an expired token
        passwordResetToken.setExpiryDate(LocalDateTime.now().minusDays(1)); // Set an expiry date in the past
        assertTrue(passwordResetTokenService.isPasswordResetTokenExpired("sampleToken"));
    }

    @Test
    public void testResetPassword() {
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .id(1L)
                .token("sampleToken")
                .appUser(new AppUser())
                .build();

        when(passwordResetTokenRepository.findByToken("sampleToken")).thenReturn(passwordResetToken);

        AppUser user = new AppUser();
        when(appUserRepository.save(Mockito.any())).thenReturn(user);

        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        passwordResetTokenService.resetPassword("sampleToken", "newPassword");

        verify(appUserRepository).save(Mockito.any());

        verify(passwordResetTokenRepository).delete(passwordResetToken);
    }
}
