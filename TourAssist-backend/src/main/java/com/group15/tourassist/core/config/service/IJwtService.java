package com.group15.tourassist.core.config.service;


import com.group15.tourassist.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author snehitroda
 * @implNote below interface is used to manage the JWT related operations for
 * managing stateless auth funcionality
 */
public interface IJwtService {
    String extractUsername(String token);

    //String generateToken(UserDetails userDetails);

    /**
     *
     * @param appUser user of the app
     * @return generated JWT token which contains the userId and roles as extra claims
     */
    String generateToken(AppUser appUser);

    String generateRefreshToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

}
