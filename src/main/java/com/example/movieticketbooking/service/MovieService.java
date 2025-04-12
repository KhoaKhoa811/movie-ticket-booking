package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.movie.request.MovieCreateRequest;
import com.example.movieticketbooking.dto.movie.request.MovieUpdateRequest;
import com.example.movieticketbooking.dto.movie.response.MovieResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface MovieService {
    MovieResponse createMovie(MovieCreateRequest movieCreateRequest, MultipartFile movieImage);
    MovieResponse getMovieById(Integer id);
    void removeMovieById(Integer id);
    MovieResponse updateMovieById(Integer id, MovieUpdateRequest movieUpdateRequest);
    MovieResponse updateMovieImage(Integer movieId, MultipartFile movieImage);
    PagedResponse<MovieResponse> getAllMovie(Pageable pageable);
    PagedResponse<MovieResponse> getAllAvailableMovieForDate(LocalDate date, Pageable pageable);
    PagedResponse<MovieResponse> getAllUpcomingMovieForDate(LocalDate date, Pageable pageable);
}
