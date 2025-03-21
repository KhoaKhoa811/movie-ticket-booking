package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.genre.request.GenreCreateRequest;
import com.example.movieticketbooking.dto.genre.response.GenreResponse;
import com.example.movieticketbooking.entity.GenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    // convert GenreCreateRequsest to GenreEntity
    GenreEntity toEntity(GenreCreateRequest request);
     // convert GenreEntity to GenreResponse
    GenreResponse toResponse(GenreEntity entity);
}
