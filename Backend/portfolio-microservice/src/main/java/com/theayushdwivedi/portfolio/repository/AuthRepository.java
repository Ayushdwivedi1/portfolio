package com.theayushdwivedi.portfolio.repository;

import com.theayushdwivedi.portfolio.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    
    Optional<Auth> findByUsername(String username);
    
    Optional<Auth> findByEmail(String email);
    
    @Query("SELECT a FROM Auth a WHERE (a.username = :usernameOrEmail OR a.email = :usernameOrEmail) AND a.isActive = true")
    Optional<Auth> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsernameOrEmail(String username, String email);
} 