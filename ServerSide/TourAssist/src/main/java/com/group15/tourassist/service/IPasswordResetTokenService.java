package com.group15.tourassist.service;

import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.PasswordResetToken;

public interface IPasswordResetTokenService {
    String generatePasswordResetToken(AppUser appUser);
    Boolean validateToken(String token);
    Boolean isPasswordResetTokenExpired(String token);
    void resetPassword(String token, String newPassword);
    String updatePasswordResetToken(PasswordResetToken token);
}
