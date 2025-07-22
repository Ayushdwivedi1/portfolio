package com.theayushdwivedi.portfolio.service.impl;

import com.theayushdwivedi.portfolio.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test") // This will only be used when the "test" profile is active
public class MockEmailServiceImpl implements EmailService {
    
    private static final Logger log = LoggerFactory.getLogger(MockEmailServiceImpl.class);
    
    @Override
    public void sendOtpEmail(String to, String otp) {
        log.info("=== MOCK EMAIL SERVICE ===");
        log.info("To: {}", to);
        log.info("Subject: Password Reset OTP");
        log.info("OTP Code: {}", otp);
        log.info("Message: Your OTP for password reset is: {}", otp);
        log.info("This OTP will expire in 10 minutes.");
        log.info("If you didn't request this, please ignore this email.");
        log.info("=== END MOCK EMAIL ===");
    }
    
    @Override
    public void sendPasswordResetEmail(String to, String resetLink) {
        log.info("=== MOCK EMAIL SERVICE ===");
        log.info("To: {}", to);
        log.info("Subject: Password Reset Link");
        log.info("Reset Link: {}", resetLink);
        log.info("Message: Click the following link to reset your password: {}", resetLink);
        log.info("This link will expire in 1 hour.");
        log.info("If you didn't request this, please ignore this email.");
        log.info("=== END MOCK EMAIL ===");
    }
} 