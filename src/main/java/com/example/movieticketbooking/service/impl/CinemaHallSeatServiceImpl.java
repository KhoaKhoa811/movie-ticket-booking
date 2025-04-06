package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.cinemaHallSeat.request.CinemaHallSeatUpdateRequest;
import com.example.movieticketbooking.dto.cinemaHallSeat.response.CinemaHallSeatResponse;
import com.example.movieticketbooking.entity.CinemaHallEntity;
import com.example.movieticketbooking.entity.CinemaHallSeatEntity;
import com.example.movieticketbooking.entity.SeatTemplateDetailEntity;
import com.example.movieticketbooking.entity.SeatTemplateEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.CinemaHallSeatMapper;
import com.example.movieticketbooking.repository.CinemaHallRepository;
import com.example.movieticketbooking.repository.CinemaHallSeatRepository;
import com.example.movieticketbooking.repository.SeatTemplateRepository;
import com.example.movieticketbooking.service.CinemaHallSeatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaHallSeatServiceImpl implements CinemaHallSeatService {
    private final CinemaHallSeatRepository cinemaHallSeatRepository;
    private final SeatTemplateRepository seatTemplateRepository;
    private final CinemaHallRepository cinemaHallRepository;
    private final CinemaHallSeatMapper cinemaHallSeatMapper;

    @Override
    @Transactional
    public void generateSeatsFromTemplate(CinemaHallEntity cinemaHallEntity, Integer seatTemplateId) {
        // get seat template by seat template id
        SeatTemplateEntity seatTemplateEntity = seatTemplateRepository.findById(seatTemplateId)
                .orElseThrow(() -> new ResourceNotFoundException(Code.SEAT_TEMPLATE_NOT_FOUND));
        // get list of seats in seat template
        List<SeatTemplateDetailEntity> details = seatTemplateEntity.getDetails();
        // mapping seat template to cinema hall seat
        List<CinemaHallSeatEntity> cinemaHallSeatEntities = details.stream()
                .map(t -> CinemaHallSeatEntity.builder()
                        .seatRow(t.getSeatRow())
                        .seatCol(t.getSeatColumn())
                        .type(t.getType())
                        .isActive(t.getIsActive())
                        .cinemaHall(cinemaHallEntity)
                        .build()
                ).toList();
        // save list of seats
        cinemaHallSeatRepository.saveAll(cinemaHallSeatEntities);
    }

    @Override
    public void deleteByCinemaHallId(Integer cinemaHallId) {
        cinemaHallSeatRepository.deleteByCinemaHallId(cinemaHallId);
    }

    @Override
    @Transactional
    public void deleteAndCreateCinemaHallSeats(CinemaHallEntity cinemaHallEntity, Integer seatTemplateId) {
        deleteByCinemaHallId(cinemaHallEntity.getId());
        generateSeatsFromTemplate(cinemaHallEntity, seatTemplateId);
    }

    @Override
    public List<CinemaHallSeatResponse> getHallSeats(Integer cinemaHallId) {
        if (!cinemaHallRepository.existsById(cinemaHallId)) {
            throw new ResourceNotFoundException(Code.CINEMA_HALL_NOT_FOUND);
        }
        List<CinemaHallSeatEntity> seatEntityList = cinemaHallSeatRepository.findByCinemaHallIdOrderById(cinemaHallId);
        return cinemaHallSeatMapper.toResponseList(seatEntityList);
    }

    @Override
    @Transactional
    public List<CinemaHallSeatResponse> updateListSeats(
            Integer cinemaHallId,
            List<CinemaHallSeatUpdateRequest> cinemaHallSeatUpdateRequests)
    {
        if (!cinemaHallRepository.existsById(cinemaHallId)) {
            throw new ResourceNotFoundException(Code.CINEMA_HALL_NOT_FOUND);
        }
        for (CinemaHallSeatUpdateRequest seat : cinemaHallSeatUpdateRequests) {
            // find seat by id
            CinemaHallSeatEntity cinemaHallSeatEntity = cinemaHallSeatRepository.findById(seat.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(Code.SEAT_NOT_FOUND));
            // mapping from update request to entity
            cinemaHallSeatMapper.updateSeatFromUpdateRequest(cinemaHallSeatEntity, seat);
            // update seat
            cinemaHallSeatRepository.save(cinemaHallSeatEntity);
        }
        return getHallSeats(cinemaHallId);
    }


}
