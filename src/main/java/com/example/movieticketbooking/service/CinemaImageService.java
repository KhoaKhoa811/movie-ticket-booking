package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.cinemaImage.CinemaImageResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CinemaImageService {
    List<CinemaImageResponse> createCinemaImage(Integer cinemaId, List<MultipartFile> images);
    List<CinemaImageEntity> uploadAndSaveImage(CinemaEntity savedCinema, List<MultipartFile> images);
    void removeCinemaImage(Integer cinemaId, Integer id);
    List<CinemaImageResponse> getCinemaImageByCinemaId(Integer cinemaId);
}
