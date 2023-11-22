package com.group15.tourassist.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request for user authentication, user for user login flow.
 * This class is annotated with Lombok annotations to generate common boilerplate code.
 * - {@code @Data}: Generates getters, setters, toString, equals, and hashCode methods.
 * - {@code @Builder}: Provides a builder pattern for easy object creation.
 * - {@code @AllArgsConstructor}: Generates a constructor with all class fields as parameters.
 * - {@code @NoArgsConstructor}: Generates a default constructor with no parameters.
 *
 * @author snehitroda
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;
}
