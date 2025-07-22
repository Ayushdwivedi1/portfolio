package com.theayushdwivedi.portfolio.service;

import com.theayushdwivedi.portfolio.dto.AuthResponse;
import com.theayushdwivedi.portfolio.dto.AuthUpdateDto;
import com.theayushdwivedi.portfolio.dto.ChangePasswordRequest;
import com.theayushdwivedi.portfolio.dto.ForgetPasswordRequest;
import com.theayushdwivedi.portfolio.dto.LoginRequest;
import com.theayushdwivedi.portfolio.dto.RegisterRequest;
import com.theayushdwivedi.portfolio.dto.VerifyOtpRequest;

import java.util.List;

public interface AuthService {
    
    AuthResponse register(RegisterRequest registerRequest);
    
    AuthResponse login(LoginRequest loginRequest);
    
    String logout(String usernameOrEmail);
    
    String forgetPassword(ForgetPasswordRequest forgetPasswordRequest);
    
    String verifyOtpAndResetPassword(VerifyOtpRequest verifyOtpRequest);
    
    String changePassword(ChangePasswordRequest changePasswordRequest);
    
    // CRUD Operations
    List<AuthResponse> getAllAuthUsers();
    
    AuthResponse getAuthUserById(Long id);
    
    AuthResponse getAuthUserByEmail(String email);
    
    AuthResponse updateAuthUser(Long id, AuthUpdateDto authUpdateDto);
    
    void deleteAuthUser(Long id);
    
    void activateAuthUser(Long id);
    
    void deactivateAuthUser(Long id);
} 