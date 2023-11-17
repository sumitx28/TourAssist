package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.Role;
import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.Customer;
import com.group15.tourassist.repository.IAgentRepository;
import com.group15.tourassist.repository.ICustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentServiceTest {

    @InjectMocks
    private AgentService agentService;

    @Mock
    private IAgentRepository agentRepository;

    private AppUser appUser;
    private Agent agent;

    @BeforeEach
    public void setup() {
        appUser = new AppUser(1L, "customer@gmail.com", Role.CUSTOMER, "abcd1234", new ArrayList<>());
        agent = new Agent(1L, appUser, "Maldives Tours", "1854568798",15, "7575645567", "abcd");
    }

    @Test
    void getAgentByAppUserId() {
        // Arrange
        when(agentRepository.findByAppUserId(1L)).thenReturn(agent);
        // Act
        Agent agentByAppUserId = agentService.getAgentByAppUserId(1L);

        // Assert
        assertEquals(agent, agentByAppUserId);
    }
}