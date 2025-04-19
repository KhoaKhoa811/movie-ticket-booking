package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.role.request.RoleRequest;
import com.example.movieticketbooking.dto.role.response.RoleResponse;
import com.example.movieticketbooking.entity.RoleEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    // convert role request to role entity
    @Mapping(target = "permissions", ignore = true)
    RoleEntity toEntity(RoleRequest roleRequest);
    // convert role entity to role response
    RoleResponse toResponse(RoleEntity roleEntity);
    List<RoleResponse> toResponseList(List<RoleEntity> roleEntities);
    // update role entity from role request
    @Mapping(target = "permissions", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRoleFromRequest(@MappingTarget RoleEntity roleEntity, RoleRequest roleRequest);
}
