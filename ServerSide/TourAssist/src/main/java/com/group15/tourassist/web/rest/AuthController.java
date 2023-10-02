package com.group15.tourassist.web.rest;

import com.group15.tourassist.dto.AgentRegistrationDto;
import com.group15.tourassist.dto.AuthResponse;
import com.group15.tourassist.dto.CustomerRegistrationDto;
import com.group15.tourassist.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/register/customer")
    private ResponseEntity<AuthResponse> registerCustomer(@RequestBody CustomerRegistrationDto customerDto) {
        AuthResponse authResponse = authService.registerCustomer(customerDto);
        return ResponseEntity.of(Optional.of(authResponse));
    }

    @PostMapping("/register/agent")
    private ResponseEntity<AuthResponse> registerAgent(@RequestBody AgentRegistrationDto agentDto) {
        AuthResponse authResponse = authService.registerAgent(agentDto);
        return ResponseEntity.of(Optional.of(authResponse));
    }
}
