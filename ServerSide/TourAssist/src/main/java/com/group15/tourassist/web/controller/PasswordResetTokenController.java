package com.group15.tourassist.web.controller;

import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.service.EmailService;
import com.group15.tourassist.service.IPasswordResetTokenService;
import com.group15.tourassist.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PasswordResetTokenController {

    private final IAppUserRepository appUserRepository;
    private final IPasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;
    Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/request")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam("email") String email) {
        log.info("here"+email);
        Optional<AppUser> userOptional = appUserRepository.findByEmail(email);
        log.info(String.valueOf(userOptional));

        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            var token = passwordResetTokenService.generatePasswordResetToken(user);
            var resetPasswordUrl = "http://localhost:5173/reset-password/" + token;
            sendResetPasswordEmail(user.getUsername(), resetPasswordUrl);
            return ResponseEntity.ok("Email sent to the user");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestParam("password") String password) {
        if (!passwordResetTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        } else {
            passwordResetTokenService.resetPassword(token, password);
            return ResponseEntity.ok("Password reset successfully.");
        }
    }

    public void sendResetPasswordEmail(String to, String resetPasswordUrl) {
        String subject = "Password Reset Request";
        String body = "Hello,\n\n"
                + "A password reset request has been sent from this email Id. Please click the following link to reset your password:\n"
                + resetPasswordUrl + "\n\n"
                + "If you didn't request this, you can safely ignore this email.\n\n";
        emailService.sendEmail(to, subject, body);

    }
}

