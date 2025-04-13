package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.show.request.ShowCreateRequest;
import com.example.movieticketbooking.dto.show.response.ShowResponse;

import java.util.List;

public interface ShowService {
    List<ShowResponse> createShows(ShowCreateRequest request);
}
