package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.config.PaginationProperties;
import com.example.movieticketbooking.dto.account.request.AccountUpdateRequest;
import com.example.movieticketbooking.dto.account.request.ChangeUserPasswordRequest;
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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable Integer id) {
        ApiResponse<AccountResponse> accountResponse = ApiResponse.<AccountResponse>builder()
                .code(Code.ACCOUNT_GET_ALL.getCode())
                .data(accountService.getAccountById(id))
                .build();
        return ResponseEntity.ok(accountResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteAccountById(@PathVariable Integer id) {
        accountService.deleteAccountById(id);
        ApiResponse<?> accountResponse = ApiResponse.builder()
                .code(Code.ACCOUNT_DELETED.getCode())
                .message(Code.ACCOUNT_DELETED.getMessage())
                .build();
        return ResponseEntity.ok(accountResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccountById(
            @PathVariable Integer id,
            @RequestBody AccountUpdateRequest accountUpdateRequest
    ) {
        ApiResponse<AccountResponse> accountResponse = ApiResponse.<AccountResponse>builder()
                .code(Code.ACCOUNT_UPDATED.getCode())
                .data(accountService.updateAccountById(id, accountUpdateRequest))
                .build();
        return ResponseEntity.ok(accountResponse);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponse<?>> changeUserPassword(@RequestBody ChangeUserPasswordRequest changeUserPasswordRequest) {
        accountService.changeUserPassword(changeUserPasswordRequest);
        ApiResponse<?> accountResponse = ApiResponse.builder()
                .code(Code.ACCOUNT_PASSWORD_CHANGED.getCode())
                .message(Code.ACCOUNT_PASSWORD_CHANGED.getMessage())
                .build();
        return ResponseEntity.ok(accountResponse);
    }


}
