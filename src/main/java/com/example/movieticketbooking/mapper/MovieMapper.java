package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.movie.request.MovieCreateRequest;
import com.example.movieticketbooking.dto.movie.request.MovieUpdateRequest;
import com.example.movieticketbooking.dto.movie.response.MovieResponse;
import com.example.movieticketbooking.entity.MovieEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GenreMapper.class})
public interface MovieMapper {
    // convert movie create request to movie entity
    @Mapping(source = "releaseDate", target = "releaseDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "genres", ignore = true)
    MovieEntity toEntity(MovieCreateRequest movieCreateRequest);
    // convert movie entity to movie response
    @Mapping(source = "releaseDate", target = "releaseDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "genres", target = "genres", qualifiedByName = "toResponseList")
    MovieResponse toResponse(MovieEntity movieEntity);
    List<MovieResponse> toResponseList(List<MovieEntity> movieEntities);
    @Mapping(target = "genres", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMovieByUpdateRequest(@MappingTarget MovieEntity movieEntity, MovieUpdateRequest movieUpdateRequest);
}
