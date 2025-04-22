package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.permission.request.PermissionRequest;
import com.example.movieticketbooking.dto.permission.response.PermissionResponse;
import com.example.movieticketbooking.entity.PermissionEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.PermissionMapper;
import com.example.movieticketbooking.repository.PermissionRepository;
import com.example.movieticketbooking.repository.RoleRepository;
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
    private final RoleRepository roleRepository;

    @Override
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        if (permissionRepository.existsByName((permissionRequest.getName()))) {
            throw new ResourceAlreadyExistsException(Code.PERMISSION_ALREADY_EXIST);
        }
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

    @Override
    public PagedResponse<PermissionResponse> getPermissionByRoleId(Integer roleId, Pageable pageable) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException(Code.ROLE_NOT_FOUND);
        }
        // find permissions by role id
        Page<PermissionEntity> permissionEntities = permissionRepository.findAllByRoleId(roleId, pageable);
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

    @Override
    public void deletePermission(Integer id) {
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.PERMISSION_NOT_FOUND);
        }
        permissionRepository.deleteById(id);
    }

    @Override
    public PermissionResponse updatePermission(Integer id, PermissionRequest permissionRequest) {
        if (permissionRepository.existsByName((permissionRequest.getName()))) {
            throw new ResourceAlreadyExistsException(Code.PERMISSION_ALREADY_EXIST);
        }
        PermissionEntity permissionEntity = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.PERMISSION_NOT_FOUND));
        permissionMapper.updatePermissionFromRequest(permissionEntity, permissionRequest);
        PermissionEntity savedEntity = permissionRepository.save(permissionEntity);
        return permissionMapper.toResponse(savedEntity);
    }
}
