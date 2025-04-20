package com.example.movieticketbooking.service.auth;

import com.example.movieticketbooking.entity.AccountEntity;

public interface VerificationTokenService {
    String generateVerificationToken(AccountEntity accountEntity);
}
