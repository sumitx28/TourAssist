package com.group15.tourassist.core.config.service;


import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author snehitroda
 * @implNote below interface is used to manage the JWT related operations for
 * managing stateless auth funcionality
 */
public interface IJwtService {
    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

}
