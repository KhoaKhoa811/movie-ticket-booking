package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.cinema.request.CinemaCreateRequest;
import com.example.movieticketbooking.dto.cinema.request.CinemaUpdateRequest;
import com.example.movieticketbooking.dto.cinema.response.CinemaResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CinemaService {
    CinemaResponse createCinema(CinemaCreateRequest cinemaCreateRequest, List<MultipartFile> images);
    CinemaResponse getCinemaById(Integer id);
    void removeCinema(Integer id);
    List<CinemaResponse> getAllCinema();
    List<CinemaResponse> getCinemaByCityId(Integer id);
    CinemaResponse updateCinema(Integer id, CinemaUpdateRequest request);
}
