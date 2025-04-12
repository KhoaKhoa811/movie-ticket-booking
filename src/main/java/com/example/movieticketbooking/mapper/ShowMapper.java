package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.show.request.ShowCreateRequest;
import com.example.movieticketbooking.entity.CinemaHallEntity;
import com.example.movieticketbooking.entity.MovieEntity;
import com.example.movieticketbooking.entity.ShowEntity;

import java.util.List;

public interface ShowMapper {
    List<ShowEntity> toShowEntities(ShowCreateRequest request, MovieEntity movie, CinemaHallEntity hall);
}
