package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.account.response.AccountResponse;
import com.example.movieticketbooking.entity.AccountEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface AccountMapper {
    // convert account entity to account response
    AccountResponse toResponse(AccountEntity accountEntity);
    List<AccountResponse> toResponseList(List<AccountEntity> accountEntities);
}
