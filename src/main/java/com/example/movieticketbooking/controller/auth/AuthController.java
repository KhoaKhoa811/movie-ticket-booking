package com.example.movieticketbooking.controller.auth;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.auth.request.*;
import com.example.movieticketbooking.dto.auth.response.LoginResponse;
import com.example.movieticketbooking.dto.auth.response.RefreshTokenResponse;
import com.example.movieticketbooking.dto.auth.response.RegisterResponse;
import com.example.movieticketbooking.dto.auth.response.VerificationTokenResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.auth.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) throws JOSEException {
        ApiResponse<LoginResponse> loginResponse =  ApiResponse.<LoginResponse>builder()
                .code(Code.LOGIN_SUCCESS.getCode())
                .data(authService.login(request))
                .build();
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<VerificationTokenResponse>> register(@RequestBody RegisterRequest request) {
        ApiResponse<VerificationTokenResponse> registerResponse = ApiResponse.<VerificationTokenResponse>builder()
                .code(Code.REGISTER_PROCESS.getCode())
                .data(authService.register(request))
                .build();
        return ResponseEntity.ok(registerResponse);
    }

    @GetMapping("/register/verify")
    public ResponseEntity<ApiResponse<RegisterResponse>> verify(@RequestParam("token") String token) {
        ApiResponse<RegisterResponse> registerResponse = ApiResponse.<RegisterResponse>builder()
                .code(Code.REGISTER_SUCCESS.getCode())
                .data(authService.verifyRegisterToken(token))
                .build();
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<VerificationTokenResponse>> forgotPassword(@RequestBody PasswordHandleEmailRequest request) {
        ApiResponse<VerificationTokenResponse> forgotPasswordResponse = ApiResponse.<VerificationTokenResponse>builder()
                .code(Code.FORGOT_PASSWORD_PROCESS.getCode())
                .data(authService.forgotPasswordHandler(request))
                .build();
        return ResponseEntity.ok(forgotPasswordResponse);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<?>> verifyForgotPassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        authService.verifyForgotPassword(changePasswordRequest);
        ApiResponse<?> verifyPasswordResponse = ApiResponse.builder()
                .code(Code.PASSWORD_CHANGED_SUCCESS.getCode())
                .message(Code.PASSWORD_CHANGED_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(verifyPasswordResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) throws JOSEException {
        ApiResponse<RefreshTokenResponse> refreshTokenResponse = ApiResponse.<RefreshTokenResponse>builder()
                .code(Code.REFRESH_TOKEN.getCode())
                .data(authService.refreshToken(request))
                .build();
        return ResponseEntity.ok(refreshTokenResponse);
    }
}
