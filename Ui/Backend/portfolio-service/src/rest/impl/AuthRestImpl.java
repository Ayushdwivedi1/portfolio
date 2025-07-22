package com.theayushdwivedi.portfolio.rest.impl;

import com.theayushdwivedi.portfolio.dto.ApiResponse;
import com.theayushdwivedi.portfolio.dto.AuthResponse;
import com.theayushdwivedi.portfolio.dto.AuthUpdateDto;
import com.theayushdwivedi.portfolio.dto.ChangePasswordRequest;
import com.theayushdwivedi.portfolio.dto.ForgetPasswordRequest;
import com.theayushdwivedi.portfolio.dto.LoginRequest;
import com.theayushdwivedi.portfolio.dto.RegisterRequest;
import com.theayushdwivedi.portfolio.dto.VerifyOtpRequest;
import com.theayushdwivedi.portfolio.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.theayushdwivedi.portfolio.service.AuthService;
import com.theayushdwivedi.portfolio.rest.AuthRest;

import java.util.List;

@Service
public class AuthRestImpl implements AuthRest {
    
    private static final Logger log = LoggerFactory.getLogger(AuthRestImpl.class);
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private EmailService emailService;
    
    @Override
    public ResponseEntity<ApiResponse<AuthResponse>> register(RegisterRequest registerRequest) {
        log.info("Registration request received for username: {}", registerRequest.getUsername());
        AuthResponse authResponse = authService.register(registerRequest);
        log.info("User registered successfully with ID: {}", authResponse.getId());
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", authResponse));
    }
    
    @Override
    public ResponseEntity<ApiResponse<AuthResponse>> login(LoginRequest loginRequest) {
        log.info("Login request received for: {}", loginRequest.getUsernameOrEmail());
        AuthResponse authResponse = authService.login(loginRequest);
        log.info("User logged in successfully with ID: {}", authResponse.getId());
        return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
    }
    
    @Override
    public ResponseEntity<ApiResponse<String>> logout(String usernameOrEmail) {
        log.info("Logout request received for: {}", usernameOrEmail);
        String result = authService.logout(usernameOrEmail);
        log.info("User logged out successfully: {}", usernameOrEmail);
        return ResponseEntity.ok(ApiResponse.success("Logout successful", result));
    }
    
    @Override
    public ResponseEntity<ApiResponse<String>> forgetPassword(ForgetPasswordRequest forgetPasswordRequest) {
        log.info("Forget password request received for email: {}", forgetPasswordRequest.getEmail());
        String result = authService.forgetPassword(forgetPasswordRequest);
        log.info("Forget password request processed successfully for email: {}", forgetPasswordRequest.getEmail());
        return ResponseEntity.ok(ApiResponse.success("OTP sent to your email", result));
    }
    
    @Override
    public ResponseEntity<ApiResponse<String>> verifyOtpAndResetPassword(VerifyOtpRequest verifyOtpRequest) {
        log.info("OTP verification request received for email: {}", verifyOtpRequest.getEmail());
        String result = authService.verifyOtpAndResetPassword(verifyOtpRequest);
        log.info("Password reset successfully for email: {}", verifyOtpRequest.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Password reset successfully", result));
    }
    
    @Override
    public ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordRequest changePasswordRequest) {
        log.info("Change password request received for: {}", changePasswordRequest.getUsernameOrEmail());
        String result = authService.changePassword(changePasswordRequest);
        log.info("Password changed successfully for: {}", changePasswordRequest.getUsernameOrEmail());
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", result));
    }
    
    @Override
    public ResponseEntity<ApiResponse<String>> testEmail(String email) {
        log.info("Test email request received for: {}", email);
        try {
            emailService.sendOtpEmail(email, "123456");
            log.info("Test email sent successfully to: {}", email);
            return ResponseEntity.ok(ApiResponse.success("Test email sent successfully"));
        } catch (Exception e) {
            log.error("Test email failed for: {} - Error: {}", email, e.getMessage());
            return ResponseEntity.ok(ApiResponse.error("Test email failed: " + e.getMessage()));
        }
    }
    
    // CRUD Operations Implementation
    
    @Override
    public ResponseEntity<ApiResponse<List<AuthResponse>>> getAllAuthUsers() {
        log.info("Get all auth users request received");
        List<AuthResponse> authUsers = authService.getAllAuthUsers();
        log.info("Retrieved {} auth users successfully", authUsers.size());
        return ResponseEntity.ok(ApiResponse.success("All auth users retrieved successfully", authUsers));
    }
    
    @Override
    public ResponseEntity<ApiResponse<AuthResponse>> getAuthUserById(Long id) {
        log.info("Get auth user by ID request received for ID: {}", id);
        AuthResponse authResponse = authService.getAuthUserById(id);
        log.info("Auth user retrieved successfully for ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Auth user retrieved successfully", authResponse));
    }
    
    @Override
    public ResponseEntity<ApiResponse<AuthResponse>> getAuthUserByEmail(String email) {
        log.info("Get auth user by email request received for email: {}", email);
        AuthResponse authResponse = authService.getAuthUserByEmail(email);
        log.info("Auth user retrieved successfully for email: {}", email);
        return ResponseEntity.ok(ApiResponse.success("Auth user retrieved successfully", authResponse));
    }
    
    @Override
    public ResponseEntity<ApiResponse<AuthResponse>> updateAuthUser(Long id, AuthUpdateDto authUpdateDto) {
        log.info("Update auth user request received for ID: {}", id);
        AuthResponse authResponse = authService.updateAuthUser(id, authUpdateDto);
        log.info("Auth user updated successfully for ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Auth user updated successfully", authResponse));
    }
    
    @Override
    public ResponseEntity<ApiResponse<String>> deleteAuthUser(Long id) {
        log.info("Delete auth user request received for ID: {}", id);
        authService.deleteAuthUser(id);
        log.info("Auth user deleted successfully for ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Auth user deleted successfully"));
    }
    
    @Override
    public ResponseEntity<ApiResponse<String>> activateAuthUser(Long id) {
        log.info("Activate auth user request received for ID: {}", id);
        authService.activateAuthUser(id);
        log.info("Auth user activated successfully for ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Auth user activated successfully"));
    }
    
    @Override
    public ResponseEntity<ApiResponse<String>> deactivateAuthUser(Long id) {
        log.info("Deactivate auth user request received for ID: {}", id);
        authService.deactivateAuthUser(id);
        log.info("Auth user deactivated successfully for ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Auth user deactivated successfully"));
    }
} 