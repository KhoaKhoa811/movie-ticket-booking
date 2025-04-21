package com.example.movieticketbooking.service.auth.impl;

import com.example.movieticketbooking.dto.auth.request.LoginRequest;
import com.example.movieticketbooking.dto.auth.request.RegisterRequest;
import com.example.movieticketbooking.dto.auth.response.LoginResponse;
import com.example.movieticketbooking.dto.auth.response.RegisterResponse;
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
import com.example.movieticketbooking.service.email.EmailSenderService;
import com.nimbusds.jose.JOSEException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final EmailSenderService emailSenderService;
    private final VerificationTokenRepository verificationTokenRepository;

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
        String jwt = jwtUtils.generateToken(authentication);
        return new LoginResponse(jwt);
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
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
        emailSenderService.sendVerificationEmail(savedEntity.getEmail(), verifyToken);
        // convert the entity to response
        return authMapper.toResponse(savedEntity);
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
}
