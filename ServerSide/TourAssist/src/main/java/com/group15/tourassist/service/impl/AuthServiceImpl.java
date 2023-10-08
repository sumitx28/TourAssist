package com.group15.tourassist.service.impl;

import com.group15.tourassist.core.ConstantUtils;
import com.group15.tourassist.core.Utils;
import com.group15.tourassist.core.enumeration.UserType;
import com.group15.tourassist.domain.Agent;
import com.group15.tourassist.domain.AppUser;
import com.group15.tourassist.domain.Customer;
import com.group15.tourassist.dto.AgentRegistrationDto;
import com.group15.tourassist.dto.AuthResponse;
import com.group15.tourassist.dto.CustomerRegistrationDto;
import com.group15.tourassist.repository.AgentRepository;
import com.group15.tourassist.repository.AppUserRepository;
import com.group15.tourassist.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Transactional
    public AuthResponse registerCustomer(CustomerRegistrationDto customerDto) {
        AuthResponse authResponse = validateCustomerRegistration(customerDto);
        if (ConstantUtils.FAILED.equals(authResponse.getStatus())) {
            return authResponse;
        }

        AppUser appUser = new AppUser();
        appUser.setEmail(customerDto.getEmail());
        appUser.setUserType(UserType.CUSTOMER);
        appUser.setPassword(customerDto.getPassword());
        appUser = appUserRepository.save(appUser);

        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setCountry(customerDto.getCountry());
        customer.setMobile(customerDto.getMobile());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setAppUser(appUser);

        customerRepository.save(customer);
        return authResponse;
    }

    private AuthResponse validateCustomerRegistration(CustomerRegistrationDto customerDto) {
        AuthResponse authResponse = AuthResponse.builder()
                                    .status(ConstantUtils.SUCCESS)
                                    .httpStatus(HttpStatus.OK)
                                    .build();

        if (!Utils.validateEmail(customerDto.getEmail())) {
            authResponse.setStatus(ConstantUtils.FAILED);
            authResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            authResponse.setMessage("Invalid Email");
            return authResponse;
        }

        if(!Utils.validatePassword(customerDto.getPassword())) {
            authResponse.setStatus(ConstantUtils.FAILED);
            authResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            authResponse.setMessage("Invalid Password");
            return authResponse;
        }

        if(!Utils.validateMobile(customerDto.getMobile())) {
            authResponse.setStatus(ConstantUtils.FAILED);
            authResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            authResponse.setMessage("Invalid Mobile");
            return authResponse;
        }

        if(!Utils.validateName(customerDto.getName())) {
            authResponse.setStatus(ConstantUtils.FAILED);
            authResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            authResponse.setMessage("Invalid Name");
            return authResponse;
        }

        if(appUserRepository.findByEmail(customerDto.getEmail()).isPresent()) {
            authResponse.setStatus(ConstantUtils.FAILED);
            authResponse.setHttpStatus(HttpStatus.GONE);
            authResponse.setMessage("User already exists");
            return authResponse;
        }

        return authResponse;
    }

    @Transactional
    public AuthResponse registerAgent(AgentRegistrationDto agentDto) {
        AuthResponse authResponse = validateAgentRegistration(agentDto);
        if (ConstantUtils.FAILED.equals(authResponse.getStatus())) {
            return authResponse;
        }

        AppUser appUser = new AppUser();
        appUser.setEmail(agentDto.getEmail());
        appUser.setUserType(UserType.AGENT);
        appUser.setPassword(agentDto.getPassword());
        appUser = appUserRepository.save(appUser);

        Agent agent = new Agent();
        agent.setCompanyName(agentDto.getCompanyName());
        agent.setEmployeeCount(agentDto.getEmployeeCount());
        agent.setMobile(agentDto.getMobile());
        agent.setVerificationId(agentDto.getVerificationId());
        agent.setVerificationDocLink(agentDto.getVerificationDocLink());
        agent.setAppUser(appUser);

        agentRepository.save(agent);
        return authResponse;
    }

    private AuthResponse validateAgentRegistration(AgentRegistrationDto agentDto) {
        AuthResponse authResponse = AuthResponse.builder()
                .status(ConstantUtils.SUCCESS)
                .httpStatus(HttpStatus.OK)
                .build();

        if (!Utils.validateEmail(agentDto.getEmail())) {
            authResponse.setStatus(ConstantUtils.FAILED);
            authResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            authResponse.setMessage("Invalid Email");
            return authResponse;
        }

        if(!Utils.validatePassword(agentDto.getPassword())) {
            authResponse.setStatus(ConstantUtils.FAILED);
            authResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            authResponse.setMessage("Invalid Password");
            return authResponse;
        }

        if(StringUtils.isEmpty(agentDto.getCompanyName())) {
            authResponse.setStatus(ConstantUtils.FAILED);
            authResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            authResponse.setMessage("Invalid Company Name");
            return authResponse;
        }

        if(StringUtils.isEmpty(agentDto.getVerificationId())) {
            authResponse.setStatus(ConstantUtils.FAILED);
            authResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            authResponse.setMessage("Invalid verification id");
            return authResponse;
        }

        if(!Utils.validateMobile(agentDto.getMobile())) {
            authResponse.setStatus(ConstantUtils.FAILED);
            authResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            authResponse.setMessage("Invalid Mobile");
            return authResponse;
        }

        if(appUserRepository.findByEmail(agentDto.getEmail()).isPresent()) {
            authResponse.setStatus(ConstantUtils.FAILED);
            authResponse.setHttpStatus(HttpStatus.GONE);
            authResponse.setMessage("User already exists");
            return authResponse;
        }

        return authResponse;
    }
}
