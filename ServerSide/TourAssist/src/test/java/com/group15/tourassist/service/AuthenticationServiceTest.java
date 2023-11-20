package com.group15.tourassist.service;

import com.group15.tourassist.core.config.service.JwtService;
import com.group15.tourassist.core.enums.BookedItem;
import com.group15.tourassist.dto.ValidateDto;
import com.group15.tourassist.entity.Activity;
import com.group15.tourassist.repository.IAgentRepository;
import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.repository.ICustomerRepository;
import com.group15.tourassist.repository.TokenRepository;
import com.group15.tourassist.request.BookingItemRequest;
import com.group15.tourassist.request.CustomerRegistrationRequest;
import com.group15.tourassist.response.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private IValidatorService validatorService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IAppUserRepository appUserRepository;

    @Mock
    private ICustomerRepository customerRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private TokenRepository tokenRepository;


    private CustomerRegistrationRequest customerRegistrationRequest;

    private ValidateDto validateDto;

    @BeforeEach
    public void setup() {
        customerRegistrationRequest = new CustomerRegistrationRequest("r.patel@dal.ca", "abcd", "Raj", "Patel", "7826645647", Instant.parse("2023-08-01T00:00:00Z"), "India");
        validateDto = new ValidateDto(HttpStatus.OK, "SUCCESS", "");
    }
    @Test
    void registerCustomer() {
        // Arrange
        when(validatorService.validateCustomerRegistration(customerRegistrationRequest)).thenReturn(validateDto);
        when(jwtService.generateToken(any())).thenReturn("token");

        // Act
        AuthenticationResponse authenticationResponse = authenticationService.registerCustomer(customerRegistrationRequest);

        // Assert
        assertNotNull(authenticationResponse.getAccessToken());
    }

    @Test
    void registerAgent() {
    }
}