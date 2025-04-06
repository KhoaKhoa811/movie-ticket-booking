package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.cinemaHallSeat.request.CinemaHallSeatUpdateRequest;
import com.example.movieticketbooking.dto.cinemaHallSeat.response.CinemaHallSeatResponse;
import com.example.movieticketbooking.entity.CinemaHallEntity;

import java.util.List;

public interface CinemaHallSeatService {
    void generateSeatsFromTemplate(CinemaHallEntity cinemaHallEntity, Integer seatTemplateId);
    void deleteByCinemaHallId(Integer cinemaHallId);
    void deleteAndCreateCinemaHallSeats(CinemaHallEntity cinemaHallEntity, Integer seatTemplateId);
    List<CinemaHallSeatResponse> getHallSeats(Integer cinemaHallId);
    List<CinemaHallSeatResponse> updateListSeats(Integer cinemaHallId, List<CinemaHallSeatUpdateRequest> cinemaHallSeatUpdateRequests);
}
