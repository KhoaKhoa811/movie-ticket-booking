package com.example.movieticketbooking.service.auth.impl;

import com.example.movieticketbooking.dto.auth.request.*;
import com.example.movieticketbooking.dto.auth.response.LoginResponse;
import com.example.movieticketbooking.dto.auth.response.RefreshTokenResponse;
import com.example.movieticketbooking.dto.auth.response.RegisterResponse;
import com.example.movieticketbooking.dto.auth.response.VerificationTokenResponse;
import com.example.movieticketbooking.entity.AccountEntity;
import com.example.movieticketbooking.entity.RoleEntity;
import com.example.movieticketbooking.entity.verification.VerificationTokenEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.enums.TokenType;
import com.example.movieticketbooking.exception.InvalidTokenSignatureException;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.auth.AuthMapper;
import com.example.movieticketbooking.repository.AccountRepository;
import com.example.movieticketbooking.repository.RoleRepository;
import com.example.movieticketbooking.repository.auth.VerificationTokenRepository;
import com.example.movieticketbooking.security.JwtUtils;
import com.example.movieticketbooking.service.auth.AuthService;
import com.example.movieticketbooking.service.auth.VerificationTokenService;
import com.nimbusds.jose.JOSEException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AccountRepository accountRepository;
    private final AuthMapper authMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserDetailsService userDetailsService;

    @Override
    public LoginResponse login(LoginRequest request) throws JOSEException {
        // authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        // save authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generate token for the user
        String accessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);
        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public VerificationTokenResponse register(RegisterRequest request) {
        // validate the request
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException(Code.ACCOUNT_EMAIL_ALREADY_EXIST);
        }
        // convert the request to entity
        AccountEntity accountEntity = authMapper.toEntity(request);
        // get roles by ids
        List<RoleEntity> roleEntities = roleRepository.findAllById(request.getRoleIds());
        accountEntity.setRoles(roleEntities);
        accountEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        accountEntity.setEnabled(false);
        // save the entity
        AccountEntity savedEntity = accountRepository.save(accountEntity);
        // generate verification token for the account
        String verifyToken = verificationTokenService.generateVerificationToken(savedEntity, TokenType.EMAIL_VERIFICATION);
        // convert the entity to response
        return new VerificationTokenResponse(verifyToken);
    }

    @Override
    @Transactional
    public RegisterResponse verifyRegisterToken(String token) {
        // get verification token
        VerificationTokenEntity verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException(Code.VERIFY_TOKEN_INVALID));
        // validate verification token
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenSignatureException(Code.VERIFY_TOKEN_INVALID);
        }
        if (verificationToken.getTokenType() != TokenType.EMAIL_VERIFICATION) {
            throw new InvalidTokenSignatureException(Code.VERIFY_TOKEN_INVALID);
        }
        // get account from verification token
        AccountEntity account = verificationToken.getAccount();
        // check if the account has already verified
        if (account.getEnabled()) {
            throw new ResourceAlreadyExistsException(Code.ACCOUNT_ALREADY_VERIFY);
        }
        account.setEnabled(true);
        AccountEntity updatedAccount = accountRepository.save(account); // update status of the account
        verificationTokenRepository.delete(verificationToken);
        return authMapper.toResponse(updatedAccount);
    }

    @Override
    public VerificationTokenResponse forgotPasswordHandler(PasswordHandleEmailRequest request) {
        AccountEntity accountEntity = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(Code.ACCOUNT_NOT_FOUND));
        // generate verification token for the account
        String verifyToken = verificationTokenService.generateVerificationToken(accountEntity, TokenType.PASSWORD_RESET);
        return new VerificationTokenResponse(verifyToken);
    }

    @Override
    public void verifyForgotPassword(ChangePasswordRequest changePasswordRequest) {
        String token = changePasswordRequest.getToken();
        String newPassword = changePasswordRequest.getNewPassword();
        // get verification token
        VerificationTokenEntity verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException(Code.VERIFY_TOKEN_INVALID));
        // validate verification token
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenSignatureException(Code.VERIFY_TOKEN_INVALID);
        }
        if (verificationToken.getTokenType() != TokenType.PASSWORD_RESET) {
            throw new InvalidTokenSignatureException(Code.VERIFY_TOKEN_INVALID);
        }
        // get account from verification token
        AccountEntity account = verificationToken.getAccount();
        // update password of the account
        account.setPassword(passwordEncoder.encode(newPassword));
        // update the account
        accountRepository.save(account);
        // delete the verification token
        verificationTokenRepository.delete(verificationToken);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        try {
            String email = jwtUtils.getEmail(request.getRefreshToken());
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            // Tạo Access Token mới
            String newAccessToken = jwtUtils.generateAccessToken(authentication);
            return new RefreshTokenResponse(newAccessToken);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
