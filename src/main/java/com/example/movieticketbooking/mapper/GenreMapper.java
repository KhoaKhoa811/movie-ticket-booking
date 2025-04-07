package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.genre.request.GenreCreateRequest;
import com.example.movieticketbooking.dto.genre.response.GenreResponse;
import com.example.movieticketbooking.entity.GenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    // convert GenreCreateRequest to GenreEntity
    GenreEntity toEntity(GenreCreateRequest request);
    List<GenreEntity> toEntityList(List<GenreCreateRequest> requests);
     // convert GenreEntity to GenreResponse
    GenreResponse toResponse(GenreEntity entity);
    @Named("toResponseList")
    List<GenreResponse> toResponseList(List<GenreEntity> entities);
    // mapping helper for mapping from dto to entity
    // Map from Integer to GenreEntity
    default GenreEntity map(Integer id) {
        return GenreEntity.builder().id(id).build();
    }
    // Map from List<Integer> to List<GenreEntity>
    List<GenreEntity> mapIdsToGenres(List<Integer> ids);
    // mapping helper for mapping from entity to dto
    // Map from GenreEntity to Integer
    default Integer map(GenreEntity genreEntity) {
        return genreEntity.getId();
    }
    // Map from List<GenEntity> to List<Integer>
    List<Integer> mapGenresToIds(List<GenreEntity> genreEntities);
}
