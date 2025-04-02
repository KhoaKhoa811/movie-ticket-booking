package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.cinemaImage.CinemaImageResponse;
import com.example.movieticketbooking.entity.CinemaImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CinemaImageMapper {
    CinemaImageResponse toResponse(CinemaImageEntity entity);
    @Named("toResponseList")
    default List<CinemaImageResponse> toResponseList(List<CinemaImageEntity> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
