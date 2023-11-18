package com.group15.tourassist.dto;

import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.DestinationMaster;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {
    private Long customerId;

    private String firstName;

    private String lastName;

    private String mobile;

    private Instant dateOfBirth;

    private String country;
}
