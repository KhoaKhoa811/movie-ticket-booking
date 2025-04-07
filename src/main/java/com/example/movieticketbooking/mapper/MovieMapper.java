package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.movie.request.MovieCreateRequest;
import com.example.movieticketbooking.dto.movie.response.MovieResponse;
import com.example.movieticketbooking.entity.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GenreMapper.class})
public interface MovieMapper {
    // convert movie create request to movie entity
    @Mapping(source = "genreIds", target = "genres")
    MovieEntity toEntity(MovieCreateRequest movieCreateRequest);
    // convert movie entity to movie response
    @Mapping(source = "genres", target = "genreIds")
    MovieResponse toResponse(MovieEntity movieEntity);
    List<MovieResponse> toResponseList(List<MovieEntity> movieEntities);
}
