package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.show.request.ShowCreateRequest;
import com.example.movieticketbooking.dto.show.response.ShowResponse;
import com.example.movieticketbooking.entity.CinemaHallEntity;
import com.example.movieticketbooking.entity.MovieEntity;
import com.example.movieticketbooking.entity.ShowEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShowMapper {
    List<ShowEntity> toShowEntities(ShowCreateRequest request, MovieEntity movie, CinemaHallEntity hall);
    ShowResponse toShowResponse(ShowEntity showEntity, MovieEntity movie, CinemaHallEntity cinemaHall);
    List<ShowResponse> toShowResponseList(List<ShowEntity> showEntities, MovieEntity movie, CinemaHallEntity cinemaHall);
}
