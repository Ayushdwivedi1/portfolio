package com.theayushdwivedi.portfolio.rest;

import com.theayushdwivedi.portfolio.dto.ApiResponse;
import com.theayushdwivedi.portfolio.dto.AuthResponse;
import com.theayushdwivedi.portfolio.dto.AuthUpdateDto;
import com.theayushdwivedi.portfolio.dto.ChangePasswordRequest;
import com.theayushdwivedi.portfolio.dto.ForgetPasswordRequest;
import com.theayushdwivedi.portfolio.dto.LoginRequest;
import com.theayushdwivedi.portfolio.dto.RegisterRequest;
import com.theayushdwivedi.portfolio.dto.VerifyOtpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public interface AuthRest {
    
    @PostMapping("/register")
    ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest registerRequest);
    
    @PostMapping("/login")
    ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest loginRequest);
    
    @PostMapping("/logout")
    ResponseEntity<ApiResponse<String>> logout(@RequestParam String usernameOrEmail);
    
    @PostMapping("/forget-password")
    ResponseEntity<ApiResponse<String>> forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest);
    
    @PostMapping("/verify-otp")
    ResponseEntity<ApiResponse<String>> verifyOtpAndResetPassword(@RequestBody VerifyOtpRequest verifyOtpRequest);
    
    @PostMapping("/change-password")
    ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest);
    
    @PostMapping("/test-email")
    ResponseEntity<ApiResponse<String>> testEmail(@RequestParam String email);
    
    // CRUD Operations for Auth Users
    
    @GetMapping("/users")
    ResponseEntity<ApiResponse<List<AuthResponse>>> getAllAuthUsers();
    
    @GetMapping("/users/{id}")
    ResponseEntity<ApiResponse<AuthResponse>> getAuthUserById(@PathVariable Long id);
    
    @GetMapping("/users/email/{email}")
    ResponseEntity<ApiResponse<AuthResponse>> getAuthUserByEmail(@PathVariable String email);
    
    @PutMapping("/users/{id}")
    ResponseEntity<ApiResponse<AuthResponse>> updateAuthUser(@PathVariable Long id, @RequestBody AuthUpdateDto authUpdateDto);
    
    @DeleteMapping("/users/{id}")
    ResponseEntity<ApiResponse<String>> deleteAuthUser(@PathVariable Long id);
    
    @PatchMapping("/users/{id}/activate")
    ResponseEntity<ApiResponse<String>> activateAuthUser(@PathVariable Long id);
    
    @PatchMapping("/users/{id}/deactivate")
    ResponseEntity<ApiResponse<String>> deactivateAuthUser(@PathVariable Long id);
} 