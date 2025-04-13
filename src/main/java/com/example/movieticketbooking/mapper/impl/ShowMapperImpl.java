package com.example.movieticketbooking.mapper.impl;

import com.example.movieticketbooking.dto.show.request.ShowCreateRequest;
import com.example.movieticketbooking.dto.show.response.ShowResponse;
import com.example.movieticketbooking.entity.CinemaHallEntity;
import com.example.movieticketbooking.entity.MovieEntity;
import com.example.movieticketbooking.entity.ShowEntity;
import com.example.movieticketbooking.mapper.ShowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShowMapperImpl implements ShowMapper {

    @Override
    public List<ShowEntity> toShowEntities(ShowCreateRequest request, MovieEntity movie, CinemaHallEntity hall) {
        return request.getStartTimeList().stream()
                .map(startTime -> ShowEntity.builder()
                        .showDate(request.getShowDate())
                        .startTime(startTime)
                        .type(request.getType())
                        .isActive(request.getIsActive())
                        .movie(movie)
                        .cinemaHall(hall)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ShowResponse toShowResponse(ShowEntity showEntity, MovieEntity movie, CinemaHallEntity cinemaHall) {
        return ShowResponse.builder()
                .id(showEntity.getId())
                .showDate(showEntity.getShowDate())
                .startTime(showEntity.getStartTime())
                .endTime(showEntity.getEndTime())
                .type(showEntity.getType())
                .isActive(showEntity.getIsActive())
                .movieId(movie.getId())
                .movieTitle(movie.getTitle())
                .cinemaHallId(cinemaHall.getId())
                .cinemaHallName(cinemaHall.getName())
                .build();
    }

    @Override
    public List<ShowResponse> toShowResponseList(List<ShowEntity> showEntities, MovieEntity movie, CinemaHallEntity cinemaHall) {
        return showEntities.stream()
                .map(entity -> toShowResponse(entity, movie, cinemaHall))
                .collect(Collectors.toList());
    }
}
