package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.cinema.request.CinemaCreateRequest;
import com.example.movieticketbooking.dto.cinema.response.CinemaCityResponse;
import com.example.movieticketbooking.dto.cinema.response.CityResponse;
import com.example.movieticketbooking.dto.cinema.response.CinemaResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaImageEntity;
import com.example.movieticketbooking.enums.CloudinaryFolderName;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.BadRequestException;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.CinemaMapper;
import com.example.movieticketbooking.repository.CinemaRepository;
import com.example.movieticketbooking.service.CinemaImageService;
import com.example.movieticketbooking.service.CinemaService;
import com.example.movieticketbooking.service.CloudinaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;
    private final CinemaImageService cinemaImageService;
    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public CinemaResponse createCinema(CinemaCreateRequest cinemaCreateRequest, List<MultipartFile> images) {
        if (cinemaRepository.existsByName(cinemaCreateRequest.getName())) {
            throw new ResourceAlreadyExistsException(Code.CINEMA_ALREADY_EXIST);
        }
        // save cinema
        CinemaEntity cinemaEntity = cinemaMapper.toEntity(cinemaCreateRequest);
        CinemaEntity savedCinema = cinemaRepository.save(cinemaEntity);
        // upload and save images
        List<CinemaImageEntity> savedImages = cinemaImageService.uploadAndSaveImage(savedCinema, images);
        // convert cinema entity to cinema response
        savedCinema.setCinemaImages(savedImages);
        return cinemaMapper.toResponse(savedCinema);
    }

    @Override
    public List<CityResponse> getAllCity() {
        return cinemaRepository.findAllCities() != null ? cinemaRepository.findAllCities() : Collections.emptyList();
    }

    @Override
    public List<CinemaCityResponse> getCinemaByCity(String city) {
        if (city == null || city.isEmpty()) {
            throw new BadRequestException(Code.CITY_INVALID);
        }
        if (!cinemaRepository.existsByCity(city)) {
            throw new ResourceNotFoundException(Code.CITY_NOT_FOUND);
        }
        List<CinemaCityResponse> results = cinemaRepository.getCinemasByCity(city);
        return results != null ? results : Collections.emptyList();
    }

    @Override
    public CinemaResponse getCinemaById(Integer id) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.CINEMA_NOT_FOUND));
        return cinemaMapper.toResponse(cinemaEntity);
    }

    @Override
    @Transactional
    public void removeCinema(Integer id) {
        if (!cinemaRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.CINEMA_NOT_FOUND);
        }
        String cinemaName = cinemaRepository.findNameById(id);
        cloudinaryService.deleteFolder(CloudinaryFolderName.CINEMA.getCinemaFolder() + "/" + cinemaName);
        cinemaRepository.deleteById(id);
    }

    @Override
    public List<CinemaResponse> getAllCinema() {
        List<CinemaEntity> cinemaEntities = cinemaRepository.findAll();
        return cinemaMapper.toResponseList(cinemaEntities);
    }


}
