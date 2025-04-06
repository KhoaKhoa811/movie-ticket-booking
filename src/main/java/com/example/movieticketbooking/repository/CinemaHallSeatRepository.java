package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.CinemaHallSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaHallSeatRepository extends JpaRepository<CinemaHallSeatEntity, Integer> {
    void deleteByCinemaHallId(Integer cinemaHallId);
    List<CinemaHallSeatEntity> findByCinemaHallIdOrderById(Integer cinemaHallId);
}
