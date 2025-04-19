package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.auth.request.LoginRequest;
import com.example.movieticketbooking.dto.auth.response.LoginResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.security.JwtUtils;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) throws JOSEException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        // save authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generate token for the user
        String jwt = jwtUtils.generateToken(authentication);
        ApiResponse<LoginResponse> loginResponse =  ApiResponse.<LoginResponse>builder()
                .code(Code.LOGIN_SUCCESS.getCode())
                .data(new LoginResponse(jwt))
                .build();
        return ResponseEntity.ok(loginResponse);
    }

}
