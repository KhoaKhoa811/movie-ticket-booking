package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.account.request.AccountUpdateRequest;
import com.example.movieticketbooking.dto.account.request.ChangeUserPasswordRequest;
import com.example.movieticketbooking.dto.account.response.AccountResponse;
import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.entity.AccountEntity;
import com.example.movieticketbooking.entity.RoleEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.IllegalArgumentException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.AccountMapper;
import com.example.movieticketbooking.repository.AccountRepository;
import com.example.movieticketbooking.repository.RoleRepository;
import com.example.movieticketbooking.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public PagedResponse<AccountResponse> getAllAccounts(Pageable pageable) {
        Page<AccountEntity> accountPage = accountRepository.findAll(pageable);
        // get a list of accounts
        List<AccountResponse> content = accountPage.getContent()
                .stream()
                .map(accountMapper::toResponse)
                .toList();
        // mapping to paged response
        return PagedResponse.<AccountResponse>builder()
                .content(content)
                .page(accountPage.getNumber())
                .size(accountPage.getSize())
                .totalElements(accountPage.getTotalElements())
                .totalPages(accountPage.getTotalPages())
                .last(accountPage.isLast())
                .build();
    }

    @Override
    public AccountResponse getAccountById(Integer id) {
        AccountEntity accountEntity = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.ACCOUNT_NOT_FOUND));
        return accountMapper.toResponse(accountEntity);
    }

    @Override
    public void deleteAccountById(Integer id) {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.ACCOUNT_NOT_FOUND);
        }
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AccountResponse updateAccountById(Integer id, AccountUpdateRequest accountUpdateRequest) {
        AccountEntity accountEntity = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.ACCOUNT_NOT_FOUND));
        if (accountUpdateRequest.getEnabled() != null) {
            accountEntity.setEnabled(accountUpdateRequest.getEnabled());
        }
        if (accountUpdateRequest.getRoleIds() != null) {
            List<RoleEntity> roleEntities = roleRepository.findAllById(accountUpdateRequest.getRoleIds());
            accountEntity.setRoles(roleEntities);
        }
        AccountEntity savedAccount = accountRepository.save(accountEntity);
        return accountMapper.toResponse(savedAccount);
    }

    @Override
    public void changeUserPassword(ChangeUserPasswordRequest changeUserPasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountEntity accountEntity = accountRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException(Code.ACCOUNT_NOT_FOUND));
        // compare the current password with the password user requests
        if (!passwordEncoder.matches(changeUserPasswordRequest.getCurrentPassword(), accountEntity.getPassword())) {
            throw new IllegalArgumentException(Code.PASSWORD_NOT_MATCH);
        }
        // encode the new password
        String encodedNewPassword = passwordEncoder.encode(changeUserPasswordRequest.getNewPassword());
        accountEntity.setPassword(encodedNewPassword);
        accountRepository.save(accountEntity);
    }
}
