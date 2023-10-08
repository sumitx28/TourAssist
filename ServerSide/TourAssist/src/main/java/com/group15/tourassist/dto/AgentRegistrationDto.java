package com.group15.tourassist.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgentRegistrationDto {

    private String email;

    private String password;

    private String companyName;

    private String mobile;

    private Integer employeeCount;

    private String verificationId;

    private String verificationDocLink;
}
