package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.genre.request.GenreCreateRequest;
import com.example.movieticketbooking.dto.genre.response.GenreResponse;
import com.example.movieticketbooking.entity.GenreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    // convert GenreCreateRequsest to GenreEntity
    GenreEntity toEntity(GenreCreateRequest request);
     // convert GenreEntity to GenreResponse
    GenreResponse toResponse(GenreEntity entity);
}
