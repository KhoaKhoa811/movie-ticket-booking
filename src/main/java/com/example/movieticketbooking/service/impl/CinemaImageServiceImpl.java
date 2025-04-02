package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.cinemaImage.CinemaImageResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaImageEntity;
import com.example.movieticketbooking.enums.CloudinaryFolderName;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.CinemaImageMapper;
import com.example.movieticketbooking.repository.CinemaImageRepository;
import com.example.movieticketbooking.repository.CinemaRepository;
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
    private final CinemaRepository cinemaRepository;
    private final CinemaImageMapper cinemaImageMapper;

    @Override
    public List<CinemaImageResponse> createCinemaImage(Integer cinemaId, List<MultipartFile> images) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new ResourceNotFoundException(Code.CINEMA_NOT_FOUND));
        List<CinemaImageEntity> uploadedImages = uploadAndSaveImage(cinemaEntity, images);
        List<CinemaImageEntity> savedImages = cinemaImageRepository.saveAll(uploadedImages);
        return cinemaImageMapper.toResponseList(savedImages);
    }

    @Override
    public List<CinemaImageEntity> uploadAndSaveImage(CinemaEntity savedCinema, List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList(); // if there's no image, return an empty list
        }
        List<CinemaImageEntity> uploadedImages = images.stream()
                .map(image -> {
                    // upload image to cloudinary
                    Map<String, Object> uploadedImage = cloudinaryService.uploadImage(
                            image, CloudinaryFolderName.CINEMA.getCinemaFolder() + "/" + savedCinema.getName()
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

    @Override
    public void removeCinemaImage(Integer cinemaId, Integer id) {
        String cinemaName = cinemaRepository.findNameById(cinemaId);
        if (cinemaName == null) {
            throw new ResourceNotFoundException(Code.CINEMA_NOT_FOUND);
        }
        CinemaImageEntity cinemaImageEntity = cinemaImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.CINEMA_IMAGES_NOT_FOUND));
        cloudinaryService.deleteImage(cinemaImageEntity.getImageUrl());
        cinemaImageRepository.deleteById(id);
    }

    @Override
    public List<CinemaImageResponse> getCinemaImageByCinemaId(Integer cinemaId) {
        if (!cinemaRepository.existsById(cinemaId)) {
            throw new ResourceNotFoundException(Code.CINEMA_NOT_FOUND);
        }
        List<CinemaImageEntity> cinemaImageEntities = cinemaImageRepository.findByCinemaId(cinemaId);
        return cinemaImageMapper.toResponseList(cinemaImageEntities);
    }
}
