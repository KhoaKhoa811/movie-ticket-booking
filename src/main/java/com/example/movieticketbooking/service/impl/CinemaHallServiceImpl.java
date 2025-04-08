package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.cinemaHall.request.CinemaHallCreateRequest;
import com.example.movieticketbooking.dto.cinemaHall.request.CinemaHallUpdateRequest;
import com.example.movieticketbooking.dto.cinemaHall.response.CinemaHallResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaHallEntity;
import com.example.movieticketbooking.entity.SeatTemplateEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.CinemaHallMapper;
import com.example.movieticketbooking.repository.CinemaHallRepository;
import com.example.movieticketbooking.repository.CinemaRepository;
import com.example.movieticketbooking.service.CinemaHallSeatService;
import com.example.movieticketbooking.service.CinemaHallService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaHallServiceImpl implements CinemaHallService {
    private final CinemaHallRepository cinemaHallRepository;
    private final CinemaRepository cinemaRepository;
    private final CinemaHallMapper cinemaHallMapper;
    @PersistenceContext
    private final EntityManager entityManager;
    private final CinemaHallSeatService cinemaHallSeatService;

    @Override
    @Transactional
    public CinemaHallResponse createCinemaHall(CinemaHallCreateRequest cinemaHallCreateRequest) {
        if (!cinemaRepository.existsById(cinemaHallCreateRequest.getCinemaId())) {
            throw new ResourceNotFoundException(Code.CINEMA_NOT_FOUND);
        }
        // mapping request to entity
        CinemaHallEntity cinemaHallEntity = cinemaHallMapper.toEntity(cinemaHallCreateRequest);
        // save cinema hall
        CinemaHallEntity savedCinemaHall = cinemaHallRepository.save(cinemaHallEntity);
        // mapping seat template to cinema hall seat
        cinemaHallSeatService.generateSeatsFromTemplate(cinemaHallEntity, cinemaHallCreateRequest.getSeatTemplateId());
        return cinemaHallMapper.toResponse(savedCinemaHall);
    }

    @Override
    @Transactional
    public List<CinemaHallResponse> getCinemaHallsByCinemaId(Integer cinemaId) {
        if (!cinemaRepository.existsById(cinemaId)) {
            throw new ResourceNotFoundException(Code.CINEMA_NOT_FOUND);
        }
        List<CinemaHallEntity> cinemaHallEntities = cinemaHallRepository.findByCinemaId(cinemaId);
        return cinemaHallMapper.toResponseList(cinemaHallEntities);
    }

    @Override
    @Transactional
    public void removeCinemaHall(Integer id) {
        if (!cinemaHallRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.CINEMA_HALL_NOT_FOUND);
        }
        cinemaHallRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CinemaHallResponse updateCinemaHall(Integer id, CinemaHallUpdateRequest cinemaHallUpdateRequest) {
        CinemaHallEntity cinemaHallEntity = cinemaHallRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.CINEMA_HALL_NOT_FOUND));
        // update cinemaHallEntity
        cinemaHallMapper.updateHallFromUpdateRequest(cinemaHallEntity, cinemaHallUpdateRequest);
        // update cinema if cinema is changed
        if (cinemaHallUpdateRequest.getCinemaId() != null) {
            CinemaEntity cinema = entityManager.getReference(CinemaEntity.class, cinemaHallUpdateRequest.getCinemaId());
            cinemaHallEntity.setCinema(cinema);
        }
        // update cinema if seat template is changed
        if (cinemaHallUpdateRequest.getSeatTemplateId() != null) {
            SeatTemplateEntity template = entityManager.getReference(SeatTemplateEntity.class, cinemaHallUpdateRequest.getSeatTemplateId());
            cinemaHallEntity.setSeatTemplate(template);

        }
        // update cinema hall
        CinemaHallEntity cinemaHallEntityUpdated = cinemaHallRepository.save(cinemaHallEntity);
        // update cinema hall seat if seat template is changed
        if (cinemaHallUpdateRequest.getSeatTemplateId() != null) {
            cinemaHallSeatService.deleteAndCreateCinemaHallSeats(cinemaHallEntityUpdated, cinemaHallUpdateRequest.getSeatTemplateId());
        }
        return cinemaHallMapper.toResponse(cinemaHallEntityUpdated);
    }

    @Override
    public CinemaHallResponse getCinemaById(Integer id) {
        CinemaHallEntity cinemaHallEntity = cinemaHallRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.CINEMA_HALL_NOT_FOUND));
        return cinemaHallMapper.toResponse(cinemaHallEntity);
    }
}
