package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.city.request.CityCreateRequest;
import com.example.movieticketbooking.dto.city.response.CityResponse;
import com.example.movieticketbooking.entity.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    CityEntity toEntity(CityCreateRequest cityCreateRequest);
    CityResponse toResponse(CityEntity cityEntity);
    CityEntity updateEntityFromResponse(CityResponse cityResponse);
}
