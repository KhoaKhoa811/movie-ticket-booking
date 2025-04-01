package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.cinema.request.CinemaCreateRequest;
import com.example.movieticketbooking.dto.cinema.request.CinemaUpdateRequest;
import com.example.movieticketbooking.dto.cinema.response.CinemaResponse;
import com.example.movieticketbooking.dto.city.response.CityResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaImageEntity;
import com.example.movieticketbooking.entity.CityEntity;
import com.example.movieticketbooking.enums.CloudinaryFolderName;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.CinemaMapper;
import com.example.movieticketbooking.mapper.CityMapper;
import com.example.movieticketbooking.repository.CinemaRepository;
import com.example.movieticketbooking.service.CinemaImageService;
import com.example.movieticketbooking.service.CinemaService;
import com.example.movieticketbooking.service.CityService;
import com.example.movieticketbooking.service.CloudinaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;
    private final CinemaImageService cinemaImageService;
    private final CloudinaryService cloudinaryService;
    private final CityService cityService;
    private final CityMapper cityMapper;

    @Override
    @Transactional
    public CinemaResponse createCinema(CinemaCreateRequest cinemaCreateRequest, List<MultipartFile> images) {
        if (cinemaRepository.existsByName(cinemaCreateRequest.getName())) {
            throw new ResourceAlreadyExistsException(Code.CINEMA_ALREADY_EXIST);
        }
        CinemaEntity cinemaEntity = cinemaMapper.toEntity(cinemaCreateRequest);
        CityEntity cityEntity;
        // Kiểm tra nếu CityRequest có ID hợp lệ thì lấy từ DB, nếu không thì tạo mới
        if (cinemaCreateRequest.getCity() != null) {
            CityResponse cityResponse = cityService.findOrCreateCity(cinemaCreateRequest.getCity());
            cityEntity = cityMapper.updateEntityFromResponse(cityResponse);
            cinemaEntity.setCity(cityEntity);
        }
        // save cinema
        CinemaEntity savedCinema = cinemaRepository.save(cinemaEntity);
        // upload and save images
        List<CinemaImageEntity> savedImages = cinemaImageService.uploadAndSaveImage(savedCinema, images);
        // convert cinema entity to cinema response
        savedCinema.setCinemaImages(savedImages);
        return cinemaMapper.toResponse(savedCinema);
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

    @Override
    public List<CinemaResponse> getCinemaByCityId(Integer id) {
        List<CinemaEntity> cinemaEntities = cinemaRepository.findByCityId(id);
        return cinemaMapper.toResponseList(cinemaEntities);
    }

    @Override
    @Transactional
    public CinemaResponse updateCinema(Integer id, CinemaUpdateRequest request) {
        if (cinemaRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException(Code.CINEMA_ALREADY_EXIST);
        }
        // get cinema need to update
        CinemaEntity cinemaEntity = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.CINEMA_NOT_FOUND));
        cinemaMapper.updateCinemaFromRequest(cinemaEntity, request);
        CityEntity cityEntity;
        if (request.getCity() != null) {
            CityResponse cityResponse = cityService.findOrCreateCity(request.getCity());
            cityEntity = cityMapper.updateEntityFromResponse(cityResponse);
            cinemaEntity.setCity(cityEntity);
        }
        // update cinema
        CinemaEntity savedCinema = cinemaRepository.save(cinemaEntity);
        return cinemaMapper.toResponse(savedCinema);
    }


}
