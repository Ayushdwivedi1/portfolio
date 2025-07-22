package com.theayushdwivedi.portfolio.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "otps")
public class Otp {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "otp_code", nullable = false)
    private String otpCode;
    
    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
    
    // Default constructor
    public Otp() {}
    
    // Constructor with fields
    public Otp(String email, String otpCode, LocalDateTime expiresAt) {
        this.email = email;
        this.otpCode = otpCode;
        this.expiresAt = expiresAt;
        this.isUsed = false;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getOtpCode() {
        return otpCode;
    }
    
    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }
    
    public Boolean getIsUsed() {
        return isUsed;
    }
    
    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    
    // Helper method to check if OTP is expired
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
    
    // Helper method to check if OTP is valid (not used and not expired)
    public boolean isValid() {
        return !isUsed && !isExpired();
    }
} 