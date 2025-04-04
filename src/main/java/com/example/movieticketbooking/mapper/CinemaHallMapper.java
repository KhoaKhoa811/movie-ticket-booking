package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.cinemaHall.request.CinemaHallCreateRequest;
import com.example.movieticketbooking.dto.cinemaHall.request.CinemaHallUpdateRequest;
import com.example.movieticketbooking.dto.cinemaHall.response.CinemaHallResponse;
import com.example.movieticketbooking.entity.CinemaHallEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CinemaHallMapper {
    CinemaHallEntity toEntity(CinemaHallCreateRequest request);
    @Mapping(source = "cinema.id", target = "cinemaId")
    CinemaHallResponse toResponse(CinemaHallEntity entity);
    @Mapping(source = "cinema.id", target = "cinemaId")
    List<CinemaHallResponse> toResponseList(List<CinemaHallEntity> entities);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateHallFromUpdateRequest(@MappingTarget CinemaHallEntity cinemaHallEntity, CinemaHallUpdateRequest request);
}
