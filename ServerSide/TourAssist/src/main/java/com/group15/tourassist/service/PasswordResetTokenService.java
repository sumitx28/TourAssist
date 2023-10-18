package com.group15.tourassist.service;

import com.group15.tourassist.core.config.service.TokenGenerator;
import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.PasswordResetToken;
import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService<EmailService> implements IPasswordResetTokenService{

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final IAppUserRepository appUserRepository;

    @Override
    public String generatePasswordResetToken(AppUser appUser) {
        String token= TokenGenerator.generateUniqueToken();
        PasswordResetToken newpasswordResetToken= new PasswordResetToken();
        newpasswordResetToken.setAppUser(appUser);
        newpasswordResetToken.setToken(token);
        LocalDateTime expiryDate= LocalDateTime.now().plusMinutes(60);
        newpasswordResetToken.setExpiryDate(expiryDate);
        passwordResetTokenRepository.save(newpasswordResetToken);
        return token;
    }

    @Override
    public Boolean validateToken(String token) {
        PasswordResetToken passwordResetToken= passwordResetTokenRepository.findByToken(token);
        return passwordResetToken!=null && !isPasswordResetTokenExpired(token) ;
    }

    @Override
    public Boolean isPasswordResetTokenExpired(String token) {
        PasswordResetToken passwordResetToken= passwordResetTokenRepository.findByToken(token);
        var expiryDate=passwordResetToken.getExpiryDate();
        LocalDateTime currentTime = LocalDateTime.now();
        return expiryDate.isBefore(currentTime) ;
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken= passwordResetTokenRepository.findByToken(token);
        AppUser user = passwordResetToken.getAppUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        appUserRepository.save(user);
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    @Override
    public void updatePasswordResetToken(PasswordResetToken token) {
        token.setToken(TokenGenerator.generateUniqueToken());
        LocalDateTime expiryDate= LocalDateTime.now().plusMinutes(60);
        token.setExpiryDate(expiryDate);
        passwordResetTokenRepository.save(token);
    }
}
