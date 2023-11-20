package com.group15.tourassist.service;

import com.group15.tourassist.dto.ValidateDto;
import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.request.AgentRegistrationRequest;
import com.group15.tourassist.request.CustomerRegistrationRequest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@ExtendWith(MockitoExtension.class)
class ValidatorServiceTest {

    @InjectMocks
    private ValidatorService validatorService;

    @Mock
    private IAppUserRepository appUserRepository;

    private CustomerRegistrationRequest customerRegistrationRequest;

    private AgentRegistrationRequest agentRegistrationRequest;

    private ValidateDto validateDto;

    @BeforeEach
    public void setup() {
        customerRegistrationRequest = new CustomerRegistrationRequest("r.patel@dal.ca", "abcd@1234", "Raj", "Patel", "7826645647", Instant.parse("2023-08-01T00:00:00Z"), "India");
        agentRegistrationRequest = new AgentRegistrationRequest("agent@dal.ca", "abcdj3#fjn", "Temple Travels", "8343454547", 20, "ahdf-dfggs-asdfs-sds", "");
        validateDto = new ValidateDto(HttpStatus.OK, "SUCCESS", "");
    }

    @Test
    void validateCustomerRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateCustomerRegistration(customerRegistrationRequest);

        // Assert
        Assert.assertEquals(HttpStatus.OK, actualResult.getHttpStatus());
    }

    @Test
    void validateAgentRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateAgentRegistration(agentRegistrationRequest);

        // Assert
        Assert.assertEquals(HttpStatus.OK, actualResult.getHttpStatus());
    }
}