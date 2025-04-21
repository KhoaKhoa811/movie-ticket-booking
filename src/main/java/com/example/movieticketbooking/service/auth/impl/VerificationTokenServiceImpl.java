package com.example.movieticketbooking.service.auth.impl;

import com.example.movieticketbooking.entity.AccountEntity;
import com.example.movieticketbooking.entity.verification.VerificationTokenEntity;
import com.example.movieticketbooking.enums.TokenType;
import com.example.movieticketbooking.repository.auth.VerificationTokenRepository;
import com.example.movieticketbooking.service.auth.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public String generateVerificationToken(AccountEntity accountEntity, TokenType tokenType) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = now.plusHours(1);
        // Tạo verification token
        String token = UUID.randomUUID().toString();
        VerificationTokenEntity vt = VerificationTokenEntity.builder()
                .token(token)
                .account(accountEntity)
                .expiryDate(expiryDate)
                .tokenType(tokenType)
                .build();
        verificationTokenRepository.save(vt);
        return token;
    }
}
