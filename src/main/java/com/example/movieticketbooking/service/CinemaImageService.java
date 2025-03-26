package com.example.movieticketbooking.service;

import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CinemaImageService {
    List<CinemaImageEntity> uploadAndSaveImage(CinemaEntity savedCinema, List<MultipartFile> images);
}
