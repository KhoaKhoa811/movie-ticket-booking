package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.role.request.RoleRequest;
import com.example.movieticketbooking.dto.role.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest roleRequest);
    List<RoleResponse> getAllRoles();
}
