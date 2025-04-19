package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.permission.request.PermissionRequest;
import com.example.movieticketbooking.dto.permission.response.PermissionResponse;
import com.example.movieticketbooking.entity.PermissionEntity;
import com.example.movieticketbooking.mapper.PermissionMapper;
import com.example.movieticketbooking.repository.PermissionRepository;
import com.example.movieticketbooking.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;

    @Override
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        PermissionEntity permissionEntity = permissionMapper.toEntity(permissionRequest);
        PermissionEntity savedEntity = permissionRepository.save(permissionEntity);
        return permissionMapper.toResponse(savedEntity);
    }
}
