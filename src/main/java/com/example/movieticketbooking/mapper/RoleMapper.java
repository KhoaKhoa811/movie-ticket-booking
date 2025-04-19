package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.role.request.RoleRequest;
import com.example.movieticketbooking.dto.role.response.RoleResponse;
import com.example.movieticketbooking.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    // convert role request to role entity
    @Mapping(target = "permissions", ignore = true)
    RoleEntity toEntity(RoleRequest roleRequest);
    // convert role entity to role response
    RoleResponse toResponse(RoleEntity roleEntity);
    List<RoleResponse> toResponseList(List<RoleEntity> roleEntities);
}
