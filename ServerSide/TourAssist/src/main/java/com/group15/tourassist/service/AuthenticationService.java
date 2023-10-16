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
import com.group15.tourassist.repository.AgentRepository;
import com.group15.tourassist.repository.CustomerRepository;
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
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;

    @Transactional
    public AuthenticationResponse registerCustomer(CustomerRegistrationRequest request) {
        ValidateDto validateDto = validateCustomerRegistration(request);
        if (ConstantUtils.FAILED.equals(validateDto.getStatus())) {
            return AuthenticationResponse.builder()
                    .accessToken(null)
                    .build();
        }

        AppUser appUser = AppUser.builder()
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .role(Role.CUSTOMER)
                            .build();
        appUser = appUserRepository.save(appUser);

        Customer customer = Customer.builder()
                .appUser(appUser)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .country(request.getCountry())
                .mobile(request.getMobile())
                .build();

        customerRepository.save(customer);

        var jwtToken = jwtService.generateToken(appUser);
        var refreshToken = jwtService.generateRefreshToken(appUser);
        saveUserToken(appUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private ValidateDto validateCustomerRegistration(CustomerRegistrationRequest request) {
        ValidateDto validateDto = ValidateDto.builder()
                .status(ConstantUtils.SUCCESS)
                .httpStatus(HttpStatus.OK)
                .build();

        if (!Utils.validateEmail(request.getEmail())) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            validateDto.setMessage("Invalid Email");
            return validateDto;
        }

        if(!Utils.validatePassword(request.getPassword())) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            validateDto.setMessage("Invalid Password");
            return validateDto;
        }

        if(!Utils.validateMobile(request.getMobile())) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            validateDto.setMessage("Invalid Mobile");
            return validateDto;
        }

        if(!Utils.validateName(request.getFirstName())) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            validateDto.setMessage("Invalid First Name");
            return validateDto;
        }

        if(!Utils.validateName(request.getLastName())) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            validateDto.setMessage("Invalid Last Name");
            return validateDto;
        }

        if(appUserRepository.findByEmail(request.getEmail()).isPresent()) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.GONE);
            validateDto.setMessage("User already exists");
            return validateDto;
        }

        return validateDto;
    }

    @Transactional
    public AuthenticationResponse registerAgent(AgentRegistrationRequest request) {
        ValidateDto validateDto = validateAgentRegistration(request);
        if (ConstantUtils.FAILED.equals(validateDto.getStatus())) {
            return AuthenticationResponse.builder()
                    .accessToken(null)
                    .build();
        }

        AppUser appUser = AppUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.AGENT)
                .build();
        appUser = appUserRepository.save(appUser);

        Agent agent = Agent.builder()
                .companyName(request.getCompanyName())
                .employeeCount(request.getEmployeeCount())
                .mobile(request.getMobile())
                .verificationId(request.getVerificationId())
                .verificationDocLink(request.getVerificationDocLink())
                .appUser(appUser)
                .build();
        agentRepository.save(agent);

        var jwtToken = jwtService.generateToken(appUser);
        var refreshToken = jwtService.generateRefreshToken(appUser);
        saveUserToken(appUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private ValidateDto validateAgentRegistration(AgentRegistrationRequest request) {
        ValidateDto validateDto = ValidateDto.builder()
                .status(ConstantUtils.SUCCESS)
                .httpStatus(HttpStatus.OK)
                .build();

        if (!Utils.validateEmail(request.getEmail())) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            validateDto.setMessage("Invalid Email");
            return validateDto;
        }

        if(!Utils.validatePassword(request.getPassword())) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            validateDto.setMessage("Invalid Password");
            return validateDto;
        }

        if(StringUtils.isEmpty(request.getCompanyName())) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            validateDto.setMessage("Invalid Company Name");
            return validateDto;
        }

        if(StringUtils.isEmpty(request.getVerificationId())) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            validateDto.setMessage("Invalid verification id");
            return validateDto;
        }

        if(!Utils.validateMobile(request.getMobile())) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            validateDto.setMessage("Invalid Mobile");
            return validateDto;
        }

        if(appUserRepository.findByEmail(request.getEmail()).isPresent()) {
            validateDto.setStatus(ConstantUtils.FAILED);
            validateDto.setHttpStatus(HttpStatus.GONE);
            validateDto.setMessage("User already exists");
            return validateDto;
        }

        return validateDto;
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
