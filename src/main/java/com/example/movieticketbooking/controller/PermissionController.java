package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.config.PaginationProperties;
import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.pagination.PaginationRequest;
import com.example.movieticketbooking.dto.permission.request.PermissionRequest;
import com.example.movieticketbooking.dto.permission.response.PermissionResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.PermissionService;
import com.example.movieticketbooking.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    private final PaginationProperties paginationProperties;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(@RequestBody PermissionRequest permissionRequest) {
        ApiResponse<PermissionResponse> permissionResponse = ApiResponse.<PermissionResponse>builder()
                .code(Code.PERMISSION_CREATED.getCode())
                .data(permissionService.createPermission(permissionRequest))
                .build();
        return ResponseEntity.ok(permissionResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<PermissionResponse>>> getAllPermissions(
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        Pageable pageable = PaginationUtils.createPageable(paginationRequest, paginationProperties);
        ApiResponse<PagedResponse<PermissionResponse>> permissionResponse = ApiResponse.<PagedResponse<PermissionResponse>>builder()
                .code(Code.PERMISSION_GET_ALL.getCode())
                .data(permissionService.getAllPermission(pageable))
                .build();
        return ResponseEntity.ok(permissionResponse);
    }

    @GetMapping("/role")
    public ResponseEntity<ApiResponse<PagedResponse<PermissionResponse>>> getPermissionByRoleId(
            @RequestParam("roleId") Integer roleId,
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        Pageable pageable = PaginationUtils.createPageable(paginationRequest, paginationProperties);
        ApiResponse<PagedResponse<PermissionResponse>> permissionResponse = ApiResponse.<PagedResponse<PermissionResponse>>builder()
                .code(Code.PERMISSION_GET_ALL.getCode())
                .data(permissionService.getPermissionByRoleId(roleId, pageable))
                .build();
        return ResponseEntity.ok(permissionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deletePermission(@PathVariable("id") Integer id) {
        permissionService.deletePermission(id);
        ApiResponse<?> permissionResponse = ApiResponse.builder()
                .code(Code.PERMISSION_DELETED.getCode())
                .message(Code.PERMISSION_DELETED.getMessage())
                .build();
        return ResponseEntity.ok(permissionResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission(
            @PathVariable("id") Integer id,
            @RequestBody PermissionRequest permissionRequest) {
        ApiResponse<PermissionResponse> permissionResponse = ApiResponse.<PermissionResponse>builder()
                .code(Code.PERMISSION_UPDATED.getCode())
                .data(permissionService.updatePermission(id, permissionRequest))
                .build();
        return ResponseEntity.ok(permissionResponse);
    }
}
