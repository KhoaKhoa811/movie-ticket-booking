package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.genre.request.GenreCreateRequest;
import com.example.movieticketbooking.dto.genre.response.GenreResponse;

public interface GenreService {
    GenreResponse createGenre(GenreCreateRequest request);
}
