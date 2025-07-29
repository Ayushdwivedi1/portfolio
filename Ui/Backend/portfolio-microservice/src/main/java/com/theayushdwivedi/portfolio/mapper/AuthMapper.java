package com.theayushdwivedi.portfolio.mapper;

import com.theayushdwivedi.portfolio.dto.AuthResponse;
import com.theayushdwivedi.portfolio.entity.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    
    private static final Logger log = LoggerFactory.getLogger(AuthMapper.class);
    
    public AuthResponse toAuthResponse(Auth auth, String message) {
        if (auth == null) {
            log.info("Converting null Auth entity to AuthResponse");
            return null;
        }
        
        log.info("Converting Auth entity to AuthResponse - ID: {}, username: {}", auth.getId(), auth.getUsername());
        AuthResponse response = new AuthResponse();
        response.setId(auth.getId());
        response.setUsername(auth.getUsername());
        response.setEmail(auth.getEmail());
        response.setFirstName(auth.getFirstName());
        response.setLastName(auth.getLastName());
        response.setLastLogin(auth.getLastLogin());
        response.setMessage(message);
        response.setToken(null); // For future JWT implementation
        
        log.info("Auth entity converted to AuthResponse successfully - ID: {}", auth.getId());
        return response;
    }
    
    public AuthResponse toAuthResponse(Auth auth, String message, String token) {
        AuthResponse response = toAuthResponse(auth, message);
        if (response != null) {
            response.setToken(token);
        }
        return response;
    }
} 