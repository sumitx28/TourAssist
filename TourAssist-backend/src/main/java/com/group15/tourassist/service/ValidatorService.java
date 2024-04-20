package com.group15.tourassist.service;

import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.core.utils.Utils;
import com.group15.tourassist.dto.ValidateDto;
import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.request.AgentRegistrationRequest;
import com.group15.tourassist.request.CustomerRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ValidatorService implements IValidatorService{

    @Autowired
    private IAppUserRepository appUserRepository;

    /**
     * @param request Customer registration request object
     * @return validation response.
     */
    @Override
    public ValidateDto validateCustomerRegistration(CustomerRegistrationRequest request) {
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

    @Override
    public ValidateDto validateAgentRegistration(AgentRegistrationRequest request) {
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
}
