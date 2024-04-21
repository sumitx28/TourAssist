package com.group15.tourassist.service.impl;

import com.group15.tourassist.dto.ValidateDto;
import com.group15.tourassist.request.AgentRegistrationRequest;
import com.group15.tourassist.request.CustomerRegistrationRequest;

public interface IValidatorService {
    public ValidateDto validateCustomerRegistration(CustomerRegistrationRequest request);

    public ValidateDto validateAgentRegistration(AgentRegistrationRequest request);
}
