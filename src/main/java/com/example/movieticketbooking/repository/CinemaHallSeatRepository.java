package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.CinemaHallSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaHallSeatRepository extends JpaRepository<CinemaHallSeatEntity, Integer> {
}
