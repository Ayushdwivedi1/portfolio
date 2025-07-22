package com.theayushdwivedi.portfolio.service.impl;

import com.theayushdwivedi.portfolio.dto.AuthResponse;
import com.theayushdwivedi.portfolio.dto.AuthUpdateDto;
import com.theayushdwivedi.portfolio.dto.ChangePasswordRequest;
import com.theayushdwivedi.portfolio.dto.ForgetPasswordRequest;
import com.theayushdwivedi.portfolio.dto.LoginRequest;
import com.theayushdwivedi.portfolio.dto.RegisterRequest;
import com.theayushdwivedi.portfolio.dto.VerifyOtpRequest;
import com.theayushdwivedi.portfolio.entity.Auth;
import com.theayushdwivedi.portfolio.entity.Otp;
import com.theayushdwivedi.portfolio.exception.AuthException;
import com.theayushdwivedi.portfolio.exception.UserAlreadyExistsException;
import com.theayushdwivedi.portfolio.exception.UserNotFoundException;
import com.theayushdwivedi.portfolio.mapper.AuthMapper;
import com.theayushdwivedi.portfolio.repository.AuthRepository;
import com.theayushdwivedi.portfolio.repository.OtpRepository;
import com.theayushdwivedi.portfolio.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthServiceImpl implements com.theayushdwivedi.portfolio.service.AuthService {
    
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    
    @Autowired
    private AuthRepository authRepository;
    
    @Autowired
    private OtpRepository otpRepository;
    
    @Autowired
    private AuthMapper authMapper;
    
    @Autowired
    private EmailService emailService;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        log.info("Starting registration process for username: {}", registerRequest.getUsername());
        
        // Validate password confirmation
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            log.info("Registration failed - password confirmation mismatch for username: {}", registerRequest.getUsername());
            throw new AuthException("Password and confirm password do not match");
        }
        
        // Check if username already exists
        if (authRepository.existsByUsername(registerRequest.getUsername())) {
            log.info("Registration failed - username already exists: {}", registerRequest.getUsername());
            throw new UserAlreadyExistsException("username", registerRequest.getUsername());
        }
        
        // Check if email already exists
        if (authRepository.existsByEmail(registerRequest.getEmail())) {
            log.info("Registration failed - email already exists: {}", registerRequest.getEmail());
            throw new UserAlreadyExistsException("email", registerRequest.getEmail());
        }
        
        log.info("Validation passed, creating new auth user");
        
        // Create new auth user
        Auth auth = new Auth();
        auth.setUsername(registerRequest.getUsername());
        auth.setEmail(registerRequest.getEmail());
        auth.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        auth.setFirstName(registerRequest.getFirstName());
        auth.setLastName(registerRequest.getLastName());
        auth.setIsActive(true);
        
        Auth savedAuth = authRepository.save(auth);
        log.info("Auth user registered successfully - ID: {}, username: {}", savedAuth.getId(), savedAuth.getUsername());
        
        return authMapper.toAuthResponse(savedAuth, "User registered successfully");
    }
    
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        log.info("Starting login process for: {}", loginRequest.getUsernameOrEmail());
        
        // Find user by username or email
        Auth auth = authRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail())
                .orElseThrow(() -> {
                    log.info("Login failed - user not found: {}", loginRequest.getUsernameOrEmail());
                    return new AuthException("Invalid username/email or password");
                });
        
        // Check if user is active
        if (!auth.getIsActive()) {
            log.info("Login failed - user is inactive: {}", loginRequest.getUsernameOrEmail());
            throw new AuthException("Account is deactivated");
        }
        
        // Verify password
        if (!passwordEncoder.matches(loginRequest.getPassword(), auth.getPassword())) {
            log.info("Login failed - invalid password for user: {}", loginRequest.getUsernameOrEmail());
            throw new AuthException("Invalid username/email or password");
        }
        
        // Update last login
        auth.setLastLogin(LocalDateTime.now());
        Auth updatedAuth = authRepository.save(auth);
        
        log.info("User logged in successfully - ID: {}, username: {}", updatedAuth.getId(), updatedAuth.getUsername());
        
        return authMapper.toAuthResponse(updatedAuth, "Login successful");
    }
    
    @Override
    public String logout(String usernameOrEmail) {
        log.info("Logout request for: {}", usernameOrEmail);
        
        // In a real application, you might want to invalidate JWT tokens here
        // For now, we'll just log the logout and return a success message
        log.info("User logged out successfully: {}", usernameOrEmail);
        return "Logout successful";
    }
    
    @Override
    public String forgetPassword(ForgetPasswordRequest forgetPasswordRequest) {
        log.info("Forget password request for email: {}", forgetPasswordRequest.getEmail());
        
        // Check if user exists
        Auth auth = authRepository.findByEmail(forgetPasswordRequest.getEmail())
                .orElseThrow(() -> {
                    log.info("Forget password failed - email not found: {}", forgetPasswordRequest.getEmail());
                    return new AuthException("Email not found");
                });
        
        // Check if user is active
        if (!auth.getIsActive()) {
            log.info("Forget password failed - user is inactive: {}", forgetPasswordRequest.getEmail());
            throw new AuthException("Account is deactivated");
        }
        
        // Generate OTP
        String otpCode = generateOtp();
        
        // Save OTP to database
        Otp otp = new Otp(forgetPasswordRequest.getEmail(), otpCode, LocalDateTime.now().plusMinutes(10));
        otpRepository.save(otp);
        
        // Send OTP via email
        try {
            emailService.sendOtpEmail(forgetPasswordRequest.getEmail(), otpCode);
            log.info("OTP sent successfully to email: {}", forgetPasswordRequest.getEmail());
            return "OTP sent to your email";
        } catch (Exception e) {
            log.error("Failed to send OTP email: {}", e.getMessage());
            // Delete the OTP from database if email sending fails
            otpRepository.delete(otp);
            throw new AuthException("Failed to send OTP email: " + e.getMessage());
        }
    }
    
    @Override
    public String verifyOtpAndResetPassword(VerifyOtpRequest verifyOtpRequest) {
        log.info("OTP verification request for email: {}", verifyOtpRequest.getEmail());
        
        // Validate password confirmation
        if (!verifyOtpRequest.getNewPassword().equals(verifyOtpRequest.getConfirmPassword())) {
            log.info("Password reset failed - password confirmation mismatch for email: {}", verifyOtpRequest.getEmail());
            throw new AuthException("Password and confirm password do not match");
        }
        
        // Find valid OTP
        Otp otp = otpRepository.findValidOtpByEmailAndCode(
                verifyOtpRequest.getEmail(), 
                verifyOtpRequest.getOtp(), 
                LocalDateTime.now()
        ).orElseThrow(() -> {
            log.info("OTP verification failed - invalid or expired OTP for email: {}", verifyOtpRequest.getEmail());
            return new AuthException("Invalid or expired OTP");
        });
        
        // Find user
        Auth auth = authRepository.findByEmail(verifyOtpRequest.getEmail())
                .orElseThrow(() -> {
                    log.info("Password reset failed - user not found: {}", verifyOtpRequest.getEmail());
                    return new AuthException("User not found");
                });
        
        // Update password
        auth.setPassword(passwordEncoder.encode(verifyOtpRequest.getNewPassword()));
        authRepository.save(auth);
        
        // Mark OTP as used
        otp.setIsUsed(true);
        otpRepository.save(otp);
        
        log.info("Password reset successfully for email: {}", verifyOtpRequest.getEmail());
        return "Password reset successfully";
    }
    
    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        log.info("Change password request for: {}", changePasswordRequest.getUsernameOrEmail());
        
        // Validate password confirmation
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            log.info("Password change failed - password confirmation mismatch for: {}", changePasswordRequest.getUsernameOrEmail());
            throw new AuthException("Password and confirm password do not match");
        }
        
        // Find user
        Auth auth = authRepository.findByUsernameOrEmail(changePasswordRequest.getUsernameOrEmail())
                .orElseThrow(() -> {
                    log.info("Password change failed - user not found: {}", changePasswordRequest.getUsernameOrEmail());
                    return new AuthException("User not found");
                });
        
        // Check if user is active
        if (!auth.getIsActive()) {
            log.info("Password change failed - user is inactive: {}", changePasswordRequest.getUsernameOrEmail());
            throw new AuthException("Account is deactivated");
        }
        
        // Verify old password
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), auth.getPassword())) {
            log.info("Password change failed - invalid old password for: {}", changePasswordRequest.getUsernameOrEmail());
            throw new AuthException("Invalid old password");
        }
        
        // Update password
        auth.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        authRepository.save(auth);
        
        log.info("Password changed successfully for: {}", changePasswordRequest.getUsernameOrEmail());
        return "Password changed successfully";
    }
    
    private String generateOtp() {
        // Generate a 6-digit OTP
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
    
    // CRUD Operations Implementation
    
    @Override
    @Transactional(readOnly = true)
    public List<AuthResponse> getAllAuthUsers() {
        log.info("Fetching all auth users from database");
        List<Auth> authUsers = authRepository.findAll();
        log.info("Retrieved {} total auth users from database", authUsers.size());
        return authUsers.stream()
                .map(auth -> authMapper.toAuthResponse(auth, "Auth user retrieved"))
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public AuthResponse getAuthUserById(Long id) {
        log.info("Fetching auth user by ID: {}", id);
        Auth auth = authRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Auth user not found for ID: {}", id);
                    return new UserNotFoundException(id);
                });
        log.info("Auth user found for ID: {} - username: {}", id, auth.getUsername());
        return authMapper.toAuthResponse(auth, "Auth user retrieved successfully");
    }
    
    @Override
    @Transactional(readOnly = true)
    public AuthResponse getAuthUserByEmail(String email) {
        log.info("Fetching auth user by email: {}", email);
        Auth auth = authRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.info("Auth user not found for email: {}", email);
                    return new UserNotFoundException("email", email);
                });
        log.info("Auth user found for email: {} - ID: {}", email, auth.getId());
        return authMapper.toAuthResponse(auth, "Auth user retrieved successfully");
    }
    
    @Override
    public AuthResponse updateAuthUser(Long id, AuthUpdateDto authUpdateDto) {
        log.info("Starting auth user update process for ID: {}", id);
        
        Auth existingAuth = authRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Auth user not found for update - ID: {}", id);
                    return new UserNotFoundException(id);
                });
        
        log.info("Found existing auth user for update - ID: {}, current username: {}", id, existingAuth.getUsername());
        
        // Check if username is being changed and if it already exists
        if (authUpdateDto.getUsername() != null && !existingAuth.getUsername().equals(authUpdateDto.getUsername()) && 
            authRepository.existsByUsername(authUpdateDto.getUsername())) {
            log.info("Auth user update failed - new username already exists: {}", authUpdateDto.getUsername());
            throw new UserAlreadyExistsException("username", authUpdateDto.getUsername());
        }
        
        // Check if email is being changed and if it already exists
        if (authUpdateDto.getEmail() != null && !existingAuth.getEmail().equals(authUpdateDto.getEmail()) && 
            authRepository.existsByEmail(authUpdateDto.getEmail())) {
            log.info("Auth user update failed - new email already exists: {}", authUpdateDto.getEmail());
            throw new UserAlreadyExistsException("email", authUpdateDto.getEmail());
        }
        
        log.info("Validation passed, proceeding with auth user update");
        
        // Update fields only if they are not null (partial update)
        if (authUpdateDto.getUsername() != null) {
            existingAuth.setUsername(authUpdateDto.getUsername());
        }
        if (authUpdateDto.getEmail() != null) {
            existingAuth.setEmail(authUpdateDto.getEmail());
        }
        if (authUpdateDto.getFirstName() != null) {
            existingAuth.setFirstName(authUpdateDto.getFirstName());
        }
        if (authUpdateDto.getLastName() != null) {
            existingAuth.setLastName(authUpdateDto.getLastName());
        }
        if (authUpdateDto.getIsActive() != null) {
            existingAuth.setIsActive(authUpdateDto.getIsActive());
        }
        
        Auth updatedAuth = authRepository.save(existingAuth);
        log.info("Auth user updated successfully - ID: {}, username: {}", updatedAuth.getId(), updatedAuth.getUsername());
        return authMapper.toAuthResponse(updatedAuth, "Auth user updated successfully");
    }
    
    @Override
    public void deleteAuthUser(Long id) {
        log.info("Starting auth user deletion process for ID: {}", id);
        Auth auth = authRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Auth user not found for deletion - ID: {}", id);
                    return new UserNotFoundException(id);
                });
        authRepository.delete(auth);
        log.info("Auth user deleted successfully - ID: {}", id);
    }
    
    @Override
    public void activateAuthUser(Long id) {
        log.info("Starting auth user activation process for ID: {}", id);
        Auth auth = authRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Auth user not found for activation - ID: {}", id);
                    return new UserNotFoundException(id);
                });
        auth.setIsActive(true);
        authRepository.save(auth);
        log.info("Auth user activated successfully - ID: {}", id);
    }
    
    @Override
    public void deactivateAuthUser(Long id) {
        log.info("Starting auth user deactivation process for ID: {}", id);
        Auth auth = authRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Auth user not found for deactivation - ID: {}", id);
                    return new UserNotFoundException(id);
                });
        auth.setIsActive(false);
        authRepository.save(auth);
        log.info("Auth user deactivated successfully - ID: {}", id);
    }
} 