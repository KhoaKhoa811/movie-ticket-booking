package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.permission.request.PermissionRequest;
import com.example.movieticketbooking.dto.permission.response.PermissionResponse;
import com.example.movieticketbooking.entity.PermissionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    // convert permission request to permission entity
    PermissionEntity toEntity(PermissionRequest permissionRequest);
    List<PermissionEntity> toEntityList(List<PermissionRequest> permissionRequests);
    // convert permission entity to permission response
    PermissionResponse toResponse(PermissionEntity permissionEntity);
    List<PermissionResponse> toResponseList(List<PermissionEntity> permissionEntities);
}
