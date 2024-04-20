package com.group15.tourassist.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegistrationRequest {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String mobile;

    private Instant dateOfBirth;

    private String country;
}
