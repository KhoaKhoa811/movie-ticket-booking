package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.cinema.request.CinemaCreateRequest;
import com.example.movieticketbooking.dto.cinema.request.CinemaUpdateRequest;
import com.example.movieticketbooking.dto.cinema.response.CinemaResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CinemaImageMapper.class, CityMapper.class})
public interface CinemaMapper {
    CinemaMapper INSTANCE = Mappers.getMapper(CinemaMapper.class);

    CinemaEntity toEntity(CinemaCreateRequest request);
    @Mapping(source = "cinemaImages", target = "images", qualifiedByName = "toResponseList")
    CinemaResponse toResponse(CinemaEntity entity);
    List<CinemaResponse> toResponseList(List<CinemaEntity> cinemaEntities);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCinemaFromRequest(@MappingTarget CinemaEntity cinema, CinemaUpdateRequest request);
}
