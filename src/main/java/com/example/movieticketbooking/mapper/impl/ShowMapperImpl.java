package com.example.movieticketbooking.mapper.impl;

import com.example.movieticketbooking.dto.show.request.ShowCreateRequest;
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
}
