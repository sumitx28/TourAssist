package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.Role;
import com.group15.tourassist.dto.ValidateDto;
import com.group15.tourassist.entity.AppUser;
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
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidatorServiceTest {

    @InjectMocks
    private ValidatorService validatorService;

    @Mock
    private IAppUserRepository appUserRepository;

    private CustomerRegistrationRequest customerRegistrationRequest;

    private AgentRegistrationRequest agentRegistrationRequest;

    private ValidateDto validateDto;

    private CustomerRegistrationRequest wrongEmailCustomer;
    private CustomerRegistrationRequest wrongPasswordCustomer;

    private CustomerRegistrationRequest wrongMobileCustomer;

    private CustomerRegistrationRequest emptyFirstName;

    private CustomerRegistrationRequest emptyLastname;

    private CustomerRegistrationRequest existingCustomer;

    private AppUser appUser;

    private AgentRegistrationRequest wrongEmailAgent;
    private AgentRegistrationRequest wrongPasswordAgent;

    private AgentRegistrationRequest wrongMobileAgent;

    private AgentRegistrationRequest nullCompanyName;

    private AgentRegistrationRequest existingAgent;

    private AgentRegistrationRequest nullVerificationId;


    private AppUser appUserAgent;


    @BeforeEach
    public void setup() {
        // Customer validation setup
        customerRegistrationRequest = new CustomerRegistrationRequest("r.patel@dal.ca", "abcd@1234", "Raj", "Patel", "7826645647", Instant.parse("2023-08-01T00:00:00Z"), "India");
        wrongEmailCustomer = new CustomerRegistrationRequest("r.pateldal.ca", "abcd@1234", "Raj", "Patel", "7826645647", Instant.parse("2023-08-01T00:00:00Z"), "India");
        wrongPasswordCustomer = new CustomerRegistrationRequest("r.patel@dal.ca", "1234", "Raj", "Patel", "7826645647", Instant.parse("2023-08-01T00:00:00Z"), "India");
        wrongMobileCustomer = new CustomerRegistrationRequest("r.patel@dal.ca", "abcd@1234", "Raj", "Patel", "1234", Instant.parse("2023-08-01T00:00:00Z"), "India");
        emptyFirstName = new CustomerRegistrationRequest("r.patel@dal.ca", "abcd@1234", "", "Patel", "7826645647", Instant.parse("2023-08-01T00:00:00Z"), "India");
        emptyLastname = new CustomerRegistrationRequest("r.patel@dal.ca", "abcd@1234", "Raj", "", "7826645647", Instant.parse("2023-08-01T00:00:00Z"), "India");
        existingCustomer = new CustomerRegistrationRequest("r.patel@dal.ca", "abcd@1234", "Raj", "Patel", "7826645647", Instant.parse("2023-08-01T00:00:00Z"), "India");


        appUser = new AppUser(1L, "r.patel@dal.ca", Role.CUSTOMER, "abcd@1234", new ArrayList<>());
        validateDto = new ValidateDto(HttpStatus.OK, "SUCCESS", "");

        // Agent validation setup
        agentRegistrationRequest = new AgentRegistrationRequest("agent@dal.ca", "abcdj3#fjn", "Temple Travels", "8343454547", 20, "ahdf-dfggs-asdfs-sds", "");
        wrongEmailAgent = new AgentRegistrationRequest("agent.gmail.ca", "abcd@1234", "Test travels", "7826645647", 20,"test", "cloud.aws.com");
        wrongPasswordAgent = new AgentRegistrationRequest("agent@dal.ca", "ab1234", "Test travels", "7826645647", 20,"test", "cloud.aws.com");
        wrongMobileAgent = new AgentRegistrationRequest("agent@dal.ca", "abcd@1234", "Test travels", "6645647", 20,"test", "cloud.aws.com");
        nullCompanyName = new AgentRegistrationRequest("agent@dal.ca", "abcd@1234", null, "7826645647", 20,"test", "cloud.aws.com");
        existingAgent = new AgentRegistrationRequest("agent@dal.ca", "abcd@1234", "Test travels", "7826645647", 20,"test", "cloud.aws.com");
        nullVerificationId =  new AgentRegistrationRequest("agent@dal.ca", "abcdj3#fjn", "Temple Travels", "8343454547", 20, null, "");

        appUserAgent = new AppUser(1L, "aagent@dal.ca", Role.AGENT, "abcd@1234", new ArrayList<>());
    }

    @Test
    void testCustomerRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateCustomerRegistration(customerRegistrationRequest);

        // Assert
        Assert.assertEquals(HttpStatus.OK, actualResult.getHttpStatus());
    }


    @Test
    void testEmailCustomerRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateCustomerRegistration(wrongEmailCustomer);

        // Assert
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getHttpStatus());
    }

    @Test
    void testPasswordCustomerRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateCustomerRegistration(wrongPasswordCustomer);

        // Assert
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getHttpStatus());
    }

    @Test
    void testMobileCustomerRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateCustomerRegistration(wrongMobileCustomer);

        // Assert
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getHttpStatus());
    }

    @Test
    void testFirstNameCustomerRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateCustomerRegistration(emptyFirstName);

        // Assert
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getHttpStatus());
    }

    @Test
    void testLastNameCustomerRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateCustomerRegistration(emptyLastname);

        // Assert
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getHttpStatus());
    }

    @Test
    void testExistingUserCustomerRegistration() {
        //Arrange
        when(appUserRepository.findByEmail("r.patel@dal.ca")).thenReturn(Optional.ofNullable(appUser));

        //Act
        ValidateDto actualResult = validatorService.validateCustomerRegistration(existingCustomer);

        // Assert
        Assert.assertEquals(HttpStatus.GONE, actualResult.getHttpStatus());
    }

    @Test
    void testAgentRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateAgentRegistration(agentRegistrationRequest);

        // Assert
        Assert.assertEquals(HttpStatus.OK, actualResult.getHttpStatus());
    }

    @Test
    void testEmailAgentRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateAgentRegistration(wrongEmailAgent);

        // Assert
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getHttpStatus());
    }

    @Test
    void testPasswordAgentRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateAgentRegistration(wrongPasswordAgent);

        // Assert
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getHttpStatus());
    }

    @Test
    void testMobileAgentRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateAgentRegistration(wrongMobileAgent);

        // Assert
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getHttpStatus());
    }

    @Test
    void testCompanyNameRegistration() {
        //Act
        ValidateDto actualResult = validatorService.validateAgentRegistration(nullCompanyName);

        // Assert
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getHttpStatus());
    }

    @Test
    void testExistingUserAgentRegistration() {
        //Arrange
        when(appUserRepository.findByEmail("agent@dal.ca")).thenReturn(Optional.ofNullable(appUserAgent));

        //Act
        ValidateDto actualResult = validatorService.validateAgentRegistration(existingAgent);

        // Assert
        Assert.assertEquals(HttpStatus.GONE, actualResult.getHttpStatus());
    }

    @Test
    void testNullVerificationIdAgentRegistration() {

        //Act
        ValidateDto actualResult = validatorService.validateAgentRegistration(nullVerificationId);

        // Assert
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getHttpStatus());
    }

}