package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.cinema.request.CinemaCreateRequest;
import com.example.movieticketbooking.dto.cinema.response.CinemaResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {CinemaImageMapper.class})
public interface CinemaMapper {
    CinemaMapper INSTANCE = Mappers.getMapper(CinemaMapper.class);

    CinemaEntity toEntity(CinemaCreateRequest request);
    @Mapping(source = "cinemaImages", target = "images", qualifiedByName = "toResponseList")
    CinemaResponse toResponse(CinemaEntity entity);
//    @Mapping(source = "city", target = "city")
//    CinemaCityResponse toCityResponse(CinemaEntity entity);
//    List<CinemaCityResponse> toCityResponseList(List<CinemaEntity> entities);
}
