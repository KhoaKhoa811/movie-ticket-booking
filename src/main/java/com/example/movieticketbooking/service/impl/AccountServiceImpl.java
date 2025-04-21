package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.account.response.AccountResponse;
import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.entity.AccountEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.AccountMapper;
import com.example.movieticketbooking.repository.AccountRepository;
import com.example.movieticketbooking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

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
}
