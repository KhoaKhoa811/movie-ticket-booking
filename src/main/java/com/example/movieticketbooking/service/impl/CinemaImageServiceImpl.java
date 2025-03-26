package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaImageEntity;
import com.example.movieticketbooking.enums.CloudinaryFolderName;
import com.example.movieticketbooking.repository.CinemaImageRepository;
import com.example.movieticketbooking.service.CinemaImageService;
import com.example.movieticketbooking.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CinemaImageServiceImpl implements CinemaImageService {
    private final CinemaImageRepository cinemaImageRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<CinemaImageEntity> uploadAndSaveImage(CinemaEntity savedCinema, List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList(); // if there's no image, return an empty list
        }
        List<CinemaImageEntity> uploadedImages = images.stream()
                .map(image -> {
                    // upload image to cloudinary
                    Map<String, Object> uploadedImage = cloudinaryService.uploadImage(
                            image, CloudinaryFolderName.CINEMA.getCinemaFolder() + savedCinema.getName()
                    );
                    // create cinema image based on cloudinary information
                    return CinemaImageEntity.builder()
                            .imageUrl(uploadedImage.get("url").toString())
                            .imageId(uploadedImage.get("public_id").toString())
                            .cinema(savedCinema)
                            .build();
                })
                .toList();
        return cinemaImageRepository.saveAll(uploadedImages);
    }
}
