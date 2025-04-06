package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.CinemaHallEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaHallRepository extends JpaRepository<CinemaHallEntity, Integer> {
    List<CinemaHallEntity> findByCinemaId(Integer cinemaId);
}
