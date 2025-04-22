package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.permission.request.PermissionRequest;
import com.example.movieticketbooking.dto.permission.response.PermissionResponse;
import com.example.movieticketbooking.entity.PermissionEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    // convert permission request to permission entity
    PermissionEntity toEntity(PermissionRequest permissionRequest);
    List<PermissionEntity> toEntityList(List<PermissionRequest> permissionRequests);
    // convert permission entity to permission response
    PermissionResponse toResponse(PermissionEntity permissionEntity);
    List<PermissionResponse> toResponseList(List<PermissionEntity> permissionEntities);
    // update permission entity from permission request
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePermissionFromRequest(@MappingTarget PermissionEntity permissionEntity, PermissionRequest permissionRequest);
}
