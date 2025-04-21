package com.example.movieticketbooking.service.auth;

import com.example.movieticketbooking.entity.AccountEntity;
import com.example.movieticketbooking.enums.TokenType;

public interface VerificationTokenService {
    String generateVerificationToken(AccountEntity accountEntity, TokenType tokenType);
}
