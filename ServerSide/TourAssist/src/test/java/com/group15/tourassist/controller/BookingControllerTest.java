package com.group15.tourassist.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.group15.tourassist.core.enums.Role;
import com.group15.tourassist.core.enums.TokenType;
import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.Customer;
import com.group15.tourassist.entity.Token;
import com.group15.tourassist.response.CustomerDetailsBookedByAgentIDResponse;
import com.group15.tourassist.service.BookingService;
import com.group15.tourassist.web.controller.BookingController;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BookingControllerTest {

    @Test
    public void testGetCustomersBookedByAgent() {
        // Create a mock instance of your bookingService
        BookingService bookingService = mock(BookingService.class);

        // Mock data for the test
        Long agentId = 123L;

        AgentDetailsDTO agentDetailsDTO1 = AgentDetailsDTO.builder()
                .agentId(1L) // Replace with a valid agent ID
                .companyName("ABC Company") // Replace with a valid company name
                .mobile("1234567890") // Replace with a valid mobile number
                .build();

        AgentDetailsDTO agentDetailsDTO2 = AgentDetailsDTO.builder()
                .agentId(1L) // Replace with a valid agent ID
                .companyName("ABC Company") // Replace with a valid company name
                .mobile("1234567890") // Replace with a valid mobile number
                .build();

        Token token1 = Token.builder()
                .token("exampleToken123")
                .tokenType(TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .build();

        AppUser appUser1 = AppUser.builder()
                .email("user1@example.com")
                .role(Role.CUSTOMER)
                .password("password1234")
                .tokens(List.of(token1))
                .build();

        Customer customer1 = Customer.builder()
                .appUser(appUser1)
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(Instant.parse("1990-01-01T00:00:00Z"))
                .country("USA")
                .mobile("1234567890")
                .build();

        Token token2 = Token.builder()
                .token("exampleToken123")
                .tokenType(TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .build();

        AppUser appUser2 = AppUser.builder()
                .email("user1@example.com")
                .role(Role.CUSTOMER)
                .password("password1234")
                .tokens(List.of(token2))
                .build();

        Customer customer2 = Customer.builder()
                .appUser(appUser2)
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(Instant.parse("1990-01-01T00:00:00Z"))
                .country("USA")
                .mobile("1234567890")
                .build();
        List<CustomerDetailsBookedByAgentIDResponse> mockServiceResponse = Arrays.asList(
                new CustomerDetailsBookedByAgentIDResponse(agentDetailsDTO1,customer1),
                new CustomerDetailsBookedByAgentIDResponse(agentDetailsDTO1,customer1)
        );

        // Set up the behavior of the mock service method
        when(bookingService.getCustomersBookedByAgentID(agentId)).thenReturn(mockServiceResponse);

        // Create an instance of your controller
        BookingController controller = new BookingController();

        // Call the controller method
        ResponseEntity<List<CustomerDetailsBookedByAgentIDResponse>> responseEntity = controller.getCustomersBookedByAgent(agentId);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockServiceResponse, responseEntity.getBody());

        // Verify that the service method was called with the correct argument
        verify(bookingService).getCustomersBookedByAgentID(agentId);
    }
}
