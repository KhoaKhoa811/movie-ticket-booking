package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.role.request.RoleRequest;
import com.example.movieticketbooking.dto.role.response.RoleResponse;
import com.example.movieticketbooking.entity.PermissionEntity;
import com.example.movieticketbooking.entity.RoleEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.mapper.RoleMapper;
import com.example.movieticketbooking.repository.PermissionRepository;
import com.example.movieticketbooking.repository.RoleRepository;
import com.example.movieticketbooking.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public RoleResponse createRole(RoleRequest roleRequest) {
        if (roleRepository.existsByName(roleRequest.getName())) {
            throw  new ResourceAlreadyExistsException(Code.ROLE_ALREADY_EXIST);
        }
        // Convert RoleRequest to RoleEntity
        RoleEntity roleEntity = roleMapper.toEntity(roleRequest);
        // find permissions by ids
        List<PermissionEntity> permissionEntities = permissionRepository.findAllById(roleRequest.getPermissionIds());
        // set permissions to role entity
        roleEntity.setPermissions(permissionEntities);
        // save role entity to db
        RoleEntity savedEntity = roleRepository.save(roleEntity);
        return roleMapper.toResponse(savedEntity);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        return roleMapper.toResponseList(roleEntities);
    }
}
