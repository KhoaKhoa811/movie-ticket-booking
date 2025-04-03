package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.cinemaHall.request.CinemaHallCreateRequest;
import com.example.movieticketbooking.dto.cinemaHall.response.CinemaHallResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaHallEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.CinemaHallMapper;
import com.example.movieticketbooking.repository.CinemaHallRepository;
import com.example.movieticketbooking.repository.CinemaRepository;
import com.example.movieticketbooking.service.CinemaHallService;
import com.example.movieticketbooking.utils.CinemaHallUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaHallServiceImpl implements CinemaHallService {
    private final CinemaHallRepository cinemaHallRepository;
    private final CinemaRepository cinemaRepository;
    private final CinemaHallMapper cinemaHallMapper;

    @Override
    public CinemaHallResponse createCinemaHall(Integer cinemaId, CinemaHallCreateRequest cinemaHallCreateRequest) {
        if (!cinemaRepository.existsById(cinemaId)) {
            throw new ResourceNotFoundException(Code.CINEMA_NOT_FOUND);
        }
        CinemaHallEntity cinemaHallEntity = cinemaHallMapper.toEntity(cinemaHallCreateRequest);
        // calculate total seats
        Integer totalSeats = CinemaHallUtils.calculateTotalSeats(cinemaHallCreateRequest.getRowCount(), cinemaHallCreateRequest.getColumnCount());
        // set total seats for cinema hall entity
        cinemaHallEntity.setTotalSeats(totalSeats);
        // set cinema id for cinema hall entity
        cinemaHallEntity.setCinema(CinemaEntity.builder().id(cinemaId).build());
        // save cinema hall
        CinemaHallEntity savedCinemaHall = cinemaHallRepository.save(cinemaHallEntity);
        return cinemaHallMapper.toResponse(savedCinemaHall);
    }

    @Override
    public List<CinemaHallResponse> getCinemaHallsByCinemaId(Integer cinemaId) {
        if (!cinemaRepository.existsById(cinemaId)) {
            throw new ResourceNotFoundException(Code.CINEMA_NOT_FOUND);
        }
        List<CinemaHallEntity> cinemaHallEntities = cinemaHallRepository.findByCinemaId(cinemaId);
        return cinemaHallMapper.toResponseList(cinemaHallEntities);
    }

    @Override
    public void removeCinemaHall(Integer cinemaId, Integer id) {
        if (!cinemaRepository.existsById(cinemaId)) {
            throw new ResourceNotFoundException(Code.CINEMA_NOT_FOUND);
        }
        if (!cinemaHallRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.CINEMA_HALL_NOT_FOUND);
        }
        cinemaHallRepository.deleteById(id);
    }
}
