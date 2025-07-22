package com.theayushdwivedi.portfolio.service.impl;

import com.theayushdwivedi.portfolio.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public void sendOtpEmail(String to, String otp) {
        log.info("Sending OTP email to: {}", to);
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ayushdwivedi920@gmail.com");
            message.setTo(to);
            message.setSubject("Password Reset OTP");
            message.setText("Your OTP for password reset is: " + otp + "\n\n" +
                          "This OTP will expire in 10 minutes.\n" +
                          "If you didn't request this, please ignore this email.");
            
            mailSender.send(message);
            log.info("OTP email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send OTP email to: {} - Error: {}", to, e.getMessage(), e);
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void sendPasswordResetEmail(String to, String resetLink) {
        log.info("Sending password reset email to: {}", to);
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Password Reset Link");
            message.setText("Click the following link to reset your password:\n\n" +
                          resetLink + "\n\n" +
                          "This link will expire in 1 hour.\n" +
                          "If you didn't request this, please ignore this email.");
            
            mailSender.send(message);
            log.info("Password reset email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", to, e);
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }
} 