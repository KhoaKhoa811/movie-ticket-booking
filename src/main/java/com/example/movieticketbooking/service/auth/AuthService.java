package com.example.movieticketbooking.service.auth;

import com.example.movieticketbooking.dto.auth.request.*;
import com.example.movieticketbooking.dto.auth.response.LoginResponse;
import com.example.movieticketbooking.dto.auth.response.RefreshTokenResponse;
import com.example.movieticketbooking.dto.auth.response.RegisterResponse;
import com.example.movieticketbooking.dto.auth.response.VerificationTokenResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthService {
    LoginResponse login(LoginRequest request) throws JOSEException;
    VerificationTokenResponse register(RegisterRequest request);
    RegisterResponse verifyRegisterToken(String token);
    VerificationTokenResponse forgotPasswordHandler(PasswordHandleEmailRequest request);
    void verifyForgotPassword(ChangePasswordRequest changePasswordRequest);
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
