package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.cinemaHall.request.CinemaHallCreateRequest;
import com.example.movieticketbooking.dto.cinemaHall.request.CinemaHallUpdateRequest;
import com.example.movieticketbooking.dto.cinemaHall.response.CinemaHallResponse;

import java.util.List;

public interface CinemaHallService {
    CinemaHallResponse createCinemaHall(CinemaHallCreateRequest cinemaHallCreateRequest);
    List<CinemaHallResponse> getCinemaHallsByCinemaId(Integer cinemaId);
    void removeCinemaHall(Integer id);
    CinemaHallResponse updateCinemaHall(Integer id, CinemaHallUpdateRequest cinemaHallUpdateRequest);
    CinemaHallResponse getCinemaById(Integer id);
}
