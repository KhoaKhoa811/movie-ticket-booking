package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.permission.request.PermissionRequest;
import com.example.movieticketbooking.dto.permission.response.PermissionResponse;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest permissionRequest);
}
