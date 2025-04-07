package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.movie.request.MovieCreateRequest;
import com.example.movieticketbooking.dto.movie.response.MovieResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService {
    MovieResponse createMovie(MovieCreateRequest movieCreateRequest, MultipartFile movieImage);
    MovieResponse getMovieById(Integer id);
}
