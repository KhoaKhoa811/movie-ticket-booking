package com.example.movieticketbooking.service.auth;

import com.example.movieticketbooking.dto.auth.request.LoginRequest;
import com.example.movieticketbooking.dto.auth.request.RegisterRequest;
import com.example.movieticketbooking.dto.auth.response.LoginResponse;
import com.example.movieticketbooking.dto.auth.response.RegisterResponse;
import com.example.movieticketbooking.dto.auth.response.VerificationTokenResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthService {
    LoginResponse login(LoginRequest request) throws JOSEException;
    VerificationTokenResponse register(RegisterRequest request);
    RegisterResponse verifyRegisterToken(String token);
}
