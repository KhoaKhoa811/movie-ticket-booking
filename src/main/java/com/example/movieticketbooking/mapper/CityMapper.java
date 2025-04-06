package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.city.request.CityRequest;
import com.example.movieticketbooking.dto.city.response.CityResponse;
import com.example.movieticketbooking.entity.CityEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityEntity toEntity(CityRequest cityRequest);
    CityResponse toResponse(CityEntity cityEntity);
    CityEntity updateEntityFromResponse(CityResponse cityResponse);
    List<CityResponse> toResponseList(List<CityEntity> cityEntities);
}
