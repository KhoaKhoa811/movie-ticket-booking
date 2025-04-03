package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.cinemaHall.request.CinemaHallCreateRequest;
import com.example.movieticketbooking.dto.cinemaHall.response.CinemaHallResponse;

public interface CinemaHallService {
    CinemaHallResponse createCinemaHall(Integer cinemaId, CinemaHallCreateRequest cinemaHallCreateRequest);
}
