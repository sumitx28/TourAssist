package com.group15.tourassist.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AgentRegistrationRequest {
    private String email;

    private String password;

    private String companyName;

    private String mobile;

    private Integer employeeCount;

    private String verificationId;

    private String verificationDocLink;
}
