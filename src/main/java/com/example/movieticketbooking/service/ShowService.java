package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.show.request.MovieAndCinemaRequest;
import com.example.movieticketbooking.dto.show.request.ShowCreateRequest;
import com.example.movieticketbooking.dto.show.response.ShowBasicResponse;
import com.example.movieticketbooking.dto.show.response.ShowResponse;

import java.time.LocalDate;
import java.util.List;

public interface ShowService {
    List<ShowResponse> createShows(ShowCreateRequest request);
    List<ShowBasicResponse> getShowsByMovieIdAndCinemaId(MovieAndCinemaRequest movieAndCinemaRequest);
    List<ShowBasicResponse> getShowsByMovieIdAndCinemaIdAndShowDate(MovieAndCinemaRequest movieAndCinemaRequest, LocalDate showDate);
    void removeShowById(Integer id);
}
