package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.Role;
import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.core.utils.Utils;
import com.group15.tourassist.core.config.service.JwtService;
import com.group15.tourassist.dto.ValidateDto;
import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.Customer;
import com.group15.tourassist.entity.Token;
import com.group15.tourassist.core.enums.TokenType;
import com.group15.tourassist.repository.IAgentRepository;
import com.group15.tourassist.repository.ICustomerRepository;
import com.group15.tourassist.request.AgentRegistrationRequest;
import com.group15.tourassist.request.AuthenticationRequest;
import com.group15.tourassist.request.CustomerRegistrationRequest;
import com.group15.tourassist.response.AuthenticationResponse;
import com.group15.tourassist.repository.TokenRepository;
import com.group15.tourassist.repository.IAppUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;


/**
 * @author snehitroda
 */

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final IAppUserRepository appUserRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ICustomerRepository ICustomerRepository;
    private final IAgentRepository IAgentRepository;
    private final IValidatorService validatorService;

    /**
     * @param request Customer registration request
     * @return Auth response.
     */
    @Transactional
    public AuthenticationResponse registerCustomer(CustomerRegistrationRequest request) {
        ValidateDto validateDto = validatorService.validateCustomerRegistration(request);
        if (ConstantUtils.FAILED.equals(validateDto.getStatus())) {
            return AuthenticationResponse.builder()
                    .accessToken(null)
                    .build();
        }

        AppUser appUser = AppUser.getAppUserForRegister(request.getEmail(), passwordEncoder.encode(request.getPassword()), Role.CUSTOMER);
        appUser = appUserRepository.save(appUser);

        Customer customer = Customer.getCustomerForRegister(request, appUser);
        ICustomerRepository.save(customer);

        var jwtToken = jwtService.generateToken(appUser);
        var refreshToken = jwtService.generateRefreshToken(appUser);
        saveUserToken(appUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    /**
     * @param request Agent registration request object
     * @return Auth response
     */
    @Transactional
    public AuthenticationResponse registerAgent(AgentRegistrationRequest request) {
        ValidateDto validateDto = validatorService.validateAgentRegistration(request);
        if (ConstantUtils.FAILED.equals(validateDto.getStatus())) {
            return AuthenticationResponse.builder()
                    .accessToken(null)
                    .build();
        }

        AppUser appUser = AppUser.getAppUserForRegister(request.getEmail(), passwordEncoder.encode(request.getPassword()), Role.AGENT);
        appUser = appUserRepository.save(appUser);

        Agent agent = Agent.getAgentForRegister(request, appUser);
        IAgentRepository.save(agent);

        var jwtToken = jwtService.generateToken(appUser);
        var refreshToken = jwtService.generateRefreshToken(appUser);
        saveUserToken(appUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }


    /**
     * @param request Authentication request containing user creds
     * @return post user authentication response
     */
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = appUserRepository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Transactional
    private void revokeAllUserTokens(AppUser appUser) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(appUser.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    @Transactional
    private void saveUserToken(AppUser appUser, String jwtToken) {
        var token = Token.builder()
                .appUser(appUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

}
