package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.show.request.ShowCreateRequest;
import com.example.movieticketbooking.dto.show.response.ShowBasicResponse;
import com.example.movieticketbooking.dto.show.response.ShowResponse;
import com.example.movieticketbooking.entity.CinemaHallEntity;
import com.example.movieticketbooking.entity.MovieEntity;
import com.example.movieticketbooking.entity.ShowEntity;

import java.util.List;

public interface ShowMapper {
    // convert from show create request to show entity
    List<ShowEntity> toShowEntities(ShowCreateRequest request, MovieEntity movie, CinemaHallEntity hall);
    // convert from show entity to show create update response
    ShowResponse toShowResponse(ShowEntity showEntity);
    List<ShowResponse> toShowResponseList(List<ShowEntity> showEntities);
    // convert from show entity to show basic response
    ShowBasicResponse toShowBasicResponse(ShowEntity showEntity);
    List<ShowBasicResponse> toShowBasicResponseList(List<ShowEntity> showEntities);

}
