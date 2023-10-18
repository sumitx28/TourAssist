package com.group15.tourassist.web.controller;

import com.group15.tourassist.entity.AppUser;
import com.group15.tourassist.entity.PasswordResetToken;
import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.repository.PasswordResetTokenRepository;
import com.group15.tourassist.request.ForgotPasswordEmailRequest;
import com.group15.tourassist.request.PasswordResetRequest;
import com.group15.tourassist.service.EmailService;
import com.group15.tourassist.service.IPasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(
        origins = {
                "http://localhost:5173",
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PasswordResetTokenController {

    private final IAppUserRepository appUserRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final IPasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;
    Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/request")
    public ResponseEntity<String> resetPasswordRequest(@RequestBody ForgotPasswordEmailRequest request) {
        log.info("here"+request.getEmail());
        Optional<AppUser> userOptional = appUserRepository.findByEmail(request.getEmail());
        if(userOptional.isPresent()){
             String token=null;
            AppUser user = userOptional.get();
            PasswordResetToken alreadyPresetToken= passwordResetTokenRepository.findByAppUser_email(request.getEmail());
            if (alreadyPresetToken!=null){
                passwordResetTokenService.updatePasswordResetToken(alreadyPresetToken);
            }
            else{
                token = passwordResetTokenService.generatePasswordResetToken(user);
            }
            var resetPasswordUrl = "http://localhost:5173/reset-password/" + token;
            sendResetPasswordEmail(user.getUsername(), resetPasswordUrl);
            return ResponseEntity.ok("Email sent to the user");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        String token= request.getToken();
        String password= request.getPassword();
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

