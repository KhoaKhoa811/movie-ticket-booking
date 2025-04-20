package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.auth.request.RegisterRequest;
import com.example.movieticketbooking.dto.auth.response.RegisterResponse;
import com.example.movieticketbooking.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface AuthMapper {
    // convert register request to account entity
    @Mapping(target = "roles", ignore = true)
    AccountEntity toEntity(RegisterRequest registerRequest);
    // convert account entity to register response
    RegisterResponse toResponse(AccountEntity accountEntity);
    List<RegisterResponse> toResponseList(List<AccountEntity> accountEntities);
}
