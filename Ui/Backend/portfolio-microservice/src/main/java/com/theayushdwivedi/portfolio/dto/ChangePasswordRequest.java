package com.theayushdwivedi.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {
    
    @NotBlank(message = "Username or email is required")
    private String usernameOrEmail;
    
    @NotBlank(message = "Old password is required")
    private String oldPassword;
    
    @NotBlank(message = "New password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;
    
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
    
    // Default constructor
    public ChangePasswordRequest() {}
    
    // Constructor with fields
    public ChangePasswordRequest(String usernameOrEmail, String oldPassword, String newPassword, String confirmPassword) {
        this.usernameOrEmail = usernameOrEmail;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
    
    // Getters and Setters
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }
    
    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }
    
    public String getOldPassword() {
        return oldPassword;
    }
    
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
} 