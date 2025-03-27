package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.cinema.request.CinemaCreateRequest;
import com.example.movieticketbooking.dto.cinema.response.CinemaCityResponse;
import com.example.movieticketbooking.dto.cinema.response.CityResponse;
import com.example.movieticketbooking.dto.cinema.response.CinemaResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaImageEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.BadRequestException;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.CinemaMapper;
import com.example.movieticketbooking.repository.CinemaRepository;
import com.example.movieticketbooking.service.CinemaImageService;
import com.example.movieticketbooking.service.CinemaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;
    private final CinemaImageService cinemaImageService;

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
}
