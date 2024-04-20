package com.group15.tourassist.service;

import com.group15.tourassist.request.AgentRegistrationRequest;
import com.group15.tourassist.request.AuthenticationRequest;
import com.group15.tourassist.request.CustomerRegistrationRequest;
import com.group15.tourassist.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IAuthenticationService {

    AuthenticationResponse registerCustomer(CustomerRegistrationRequest request);

    AuthenticationResponse registerAgent(AgentRegistrationRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
