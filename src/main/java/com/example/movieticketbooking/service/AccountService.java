package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.account.request.AccountUpdateRequest;
import com.example.movieticketbooking.dto.account.response.AccountResponse;
import com.example.movieticketbooking.dto.api.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    PagedResponse<AccountResponse> getAllAccounts(Pageable pageable);
    AccountResponse getAccountById(Integer id);
    void deleteAccountById(Integer id);
    AccountResponse updateAccountById(Integer id, AccountUpdateRequest accountUpdateRequest);
}
