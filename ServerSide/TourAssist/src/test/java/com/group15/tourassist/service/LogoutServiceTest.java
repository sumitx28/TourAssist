package com.group15.tourassist.service;

import com.group15.tourassist.core.config.LogoutService;
import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.entity.Token;
import com.group15.tourassist.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class LogoutServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private LogoutService logoutService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogoutWithValidJwtToken() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        String validJwtToken = "validJwtToken";
        when(request.getHeader(ConstantUtils.AUTHORIZATION)).thenReturn(ConstantUtils.BEARER + validJwtToken);
        when(tokenRepository.findByToken(validJwtToken)).thenReturn(Optional.of(new Token()));

        // Act
        logoutService.logout(request, response, authentication);

        // Assert
        verify(tokenRepository, times(1)).findByToken(validJwtToken);
        verify(tokenRepository, times(1)).save(any()); // You might want to use ArgumentMatchers if the token is complex
    }

}
