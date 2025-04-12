package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.movie.request.MovieCreateRequest;
import com.example.movieticketbooking.dto.movie.storage.UploadImage;
import com.example.movieticketbooking.enums.CloudinaryFolderName;
import com.example.movieticketbooking.service.CloudinaryService;
import com.example.movieticketbooking.service.MovieImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieImageServiceImpl implements MovieImageService {
    private final CloudinaryService cloudinaryService;


    @Override
    public UploadImage uploadMovieImage(String title, MultipartFile movieImage) {
        Map<String, Object> uploadedImage = cloudinaryService.uploadImage(
                movieImage, CloudinaryFolderName.MOVIE.getCinemaFolder() + "/" + title
        );
        return UploadImage.builder()
                .imageId(uploadedImage.get("public_id").toString())
                .imagePath(uploadedImage.get("url").toString())
                .build();
    }


}
