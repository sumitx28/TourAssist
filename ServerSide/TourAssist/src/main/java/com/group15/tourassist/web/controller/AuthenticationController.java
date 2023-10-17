package com.group15.tourassist.web.controller;

import com.group15.tourassist.request.AgentRegistrationRequest;
import com.group15.tourassist.request.CustomerRegistrationRequest;
import com.group15.tourassist.service.IAuthenticationService;
import com.group15.tourassist.request.AuthenticationRequest;
import com.group15.tourassist.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin(
        origins = {
                "http://localhost:5173",
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;
    Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register/customer")
    private ResponseEntity<AuthenticationResponse> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        log.info("** register request: {}", request.toString());
        AuthenticationResponse authResponse = authenticationService.registerCustomer(request);
        return ResponseEntity.of(Optional.of(authResponse));
    }

    @PostMapping("/register/agent")
    private ResponseEntity<AuthenticationResponse> registerAgent(@RequestBody AgentRegistrationRequest request) {
        AuthenticationResponse authResponse = authenticationService.registerAgent(request);
        return ResponseEntity.of(Optional.of(authResponse));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("** login request: {}", request.getEmail());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

}
