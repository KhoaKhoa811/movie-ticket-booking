package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.movie.request.MovieCreateRequest;
import com.example.movieticketbooking.dto.movie.storage.UploadImage;
import org.springframework.web.multipart.MultipartFile;

public interface MovieImageService {
    UploadImage uploadMovieImage(String title, MultipartFile movieImage);
}
