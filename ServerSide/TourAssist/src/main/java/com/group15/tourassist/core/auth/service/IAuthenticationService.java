package com.group15.tourassist.core.auth.service;

import com.group15.tourassist.core.request.AuthenticationRequest;
import com.group15.tourassist.core.request.RegisterRequest;
import com.group15.tourassist.core.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IAuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
