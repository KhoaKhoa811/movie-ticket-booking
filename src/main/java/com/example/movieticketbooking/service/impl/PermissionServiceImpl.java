package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.permission.request.PermissionRequest;
import com.example.movieticketbooking.dto.permission.response.PermissionResponse;
import com.example.movieticketbooking.entity.PermissionEntity;
import com.example.movieticketbooking.mapper.PermissionMapper;
import com.example.movieticketbooking.repository.PermissionRepository;
import com.example.movieticketbooking.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PagedResponse<PermissionResponse> getAllPermission(Pageable pageable) {
        // find all permissions
        Page<PermissionEntity> permissionEntities = permissionRepository.findAll(pageable);
        // get a list of permissions
        List<PermissionResponse> permissionResponses = permissionMapper.toResponseList(permissionEntities.getContent());
        // mapping to paged response
        return PagedResponse.<PermissionResponse>builder()
                .content(permissionResponses)
                .page(permissionEntities.getNumber())
                .size(permissionEntities.getSize())
                .totalElements(permissionEntities.getTotalElements())
                .totalPages(permissionEntities.getTotalPages())
                .last(permissionEntities.isLast())
                .build();
    }
}
