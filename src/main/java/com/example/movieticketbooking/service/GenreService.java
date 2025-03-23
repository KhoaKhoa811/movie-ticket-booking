package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.genre.request.GenreCreateRequest;
import com.example.movieticketbooking.dto.genre.response.GenreResponse;

import java.util.List;

public interface GenreService {
    GenreResponse createGenre(GenreCreateRequest request);
    void removeGenre(Integer id);
    List<GenreResponse> getAllGenre();
}
