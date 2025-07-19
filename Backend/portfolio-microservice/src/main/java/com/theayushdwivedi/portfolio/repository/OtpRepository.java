package com.theayushdwivedi.portfolio.repository;

import com.theayushdwivedi.portfolio.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    
    @Query("SELECT o FROM Otp o WHERE o.email = :email AND o.isUsed = false AND o.expiresAt > :now ORDER BY o.createdAt DESC")
    List<Otp> findValidOtpsByEmail(@Param("email") String email, @Param("now") LocalDateTime now);
    
    @Query("SELECT o FROM Otp o WHERE o.email = :email AND o.otpCode = :otpCode AND o.isUsed = false AND o.expiresAt > :now")
    Optional<Otp> findValidOtpByEmailAndCode(@Param("email") String email, @Param("otpCode") String otpCode, @Param("now") LocalDateTime now);
    
    void deleteByEmail(String email);
    
    void deleteByExpiresAtBefore(LocalDateTime expiresAt);
} 