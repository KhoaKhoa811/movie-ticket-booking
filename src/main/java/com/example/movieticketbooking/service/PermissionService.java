package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.permission.request.PermissionRequest;
import com.example.movieticketbooking.dto.permission.response.PermissionResponse;
import org.springframework.data.domain.Pageable;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest permissionRequest);
    PagedResponse<PermissionResponse> getAllPermission(Pageable pageable);
    PagedResponse<PermissionResponse> getPermissionByRoleId(Integer roleId, Pageable pageable);
    void deletePermission(Integer id);
    PermissionResponse updatePermission(Integer id, PermissionRequest permissionRequest);
}
