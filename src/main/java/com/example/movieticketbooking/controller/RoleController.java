package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.role.request.RoleRequest;
import com.example.movieticketbooking.dto.role.response.RoleResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody RoleRequest roleRequest) {
        ApiResponse<RoleResponse> roleResponse = ApiResponse.<RoleResponse>builder()
                .code(Code.ROLE_CREATED.getCode())
                .data(roleService.createRole(roleRequest))
                .build();
        return ResponseEntity.ok(roleResponse);
    }
}
