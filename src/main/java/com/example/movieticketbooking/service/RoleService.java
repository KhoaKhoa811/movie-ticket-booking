package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.role.request.RoleRequest;
import com.example.movieticketbooking.dto.role.response.RoleResponse;

public interface RoleService {
    RoleResponse createRole(RoleRequest roleRequest);
}
