package com.example.movieticketbooking.repository.auth;

import com.example.movieticketbooking.entity.verification.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Integer> {
    Optional<VerificationTokenEntity> findByToken(String token);
}
