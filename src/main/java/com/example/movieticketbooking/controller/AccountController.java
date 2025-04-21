package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.config.PaginationProperties;
import com.example.movieticketbooking.dto.account.response.AccountResponse;
import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.pagination.PaginationRequest;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.AccountService;
import com.example.movieticketbooking.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final PaginationProperties paginationProperties;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<AccountResponse>>> getAllAccounts(@ModelAttribute PaginationRequest paginationRequest) {
        Pageable pageable = PaginationUtils.createPageable(paginationRequest, paginationProperties);
        ApiResponse<PagedResponse<AccountResponse>> accountResponse = ApiResponse.<PagedResponse<AccountResponse>>builder()
                .code(Code.ACCOUNT_GET_ALL.getCode())
                .data(accountService.getAllAccounts(pageable))
                .build();
        return ResponseEntity.ok(accountResponse);
    }
}
