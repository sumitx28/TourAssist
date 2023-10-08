package com.group15.tourassist.core.config;

import com.group15.tourassist.core.ConstantUtils;
import com.group15.tourassist.core.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;


/**
 * @author snehitroda
 */

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader(ConstantUtils.AUTHORIZATION);
        final String jwt;
        if (authHeader == null || !authHeader.startsWith(ConstantUtils.BEARER)) {
            return;
        }
        jwt = authHeader.substring(7);
        var existingToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (existingToken != null) {
            existingToken.setExpired(true);
            existingToken.setRevoked(true);
            tokenRepository.save(existingToken);
            SecurityContextHolder.clearContext();
        }
    }
}
