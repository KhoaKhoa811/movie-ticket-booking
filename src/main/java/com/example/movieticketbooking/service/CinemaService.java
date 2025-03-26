package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.cinema.request.CinemaCreateRequest;
import com.example.movieticketbooking.dto.cinema.response.CinemaResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CinemaService {
    CinemaResponse createCinema(CinemaCreateRequest cinemaCreateRequest, List<MultipartFile> images);
}
