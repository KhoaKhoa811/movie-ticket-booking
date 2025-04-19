package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.role.request.RoleRequest;
import com.example.movieticketbooking.dto.role.response.RoleResponse;
import com.example.movieticketbooking.entity.PermissionEntity;
import com.example.movieticketbooking.entity.RoleEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.RoleMapper;
import com.example.movieticketbooking.repository.AccountRepository;
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
    private final AccountRepository accountRepository;

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

    @Override
    public List<RoleResponse> getRoleByAccountId(Integer accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException(Code.ACCOUNT_NOT_FOUND);
        }
        List<RoleEntity> roleEntities = roleRepository.findByAccountId(accountId);
        return roleMapper.toResponseList(roleEntities);
    }

    @Override
    public void deleteRole(Integer id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.ROLE_NOT_FOUND);
        }
        roleRepository.deleteById(id);
    }

    @Override
    public RoleResponse updateRole(Integer id, RoleRequest roleRequest) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.ROLE_NOT_FOUND);
        }
        RoleEntity roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.ROLE_NOT_FOUND));
        // update role entity
        roleMapper.updateRoleFromRequest(roleEntity, roleRequest);
        // find permissions by ids
        if (roleRequest.getPermissionIds() != null) {
            List<PermissionEntity> permissionEntities = permissionRepository.findAllById(roleRequest.getPermissionIds());
            // set permissions to role entity
            roleEntity.setPermissions(permissionEntities);
        }
        // save role entity to db
        RoleEntity updatedEntity = roleRepository.save(roleEntity);
        return roleMapper.toResponse(updatedEntity);
    }
}
