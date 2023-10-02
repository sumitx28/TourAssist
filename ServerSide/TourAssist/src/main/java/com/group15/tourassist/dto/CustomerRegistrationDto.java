package com.group15.tourassist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRegistrationDto {

    private String email;

    private String password;

    private String securityQuestions;

    private String name;

    private Long mobile;

    private Instant dateOfBirth;

    private String country;
}
