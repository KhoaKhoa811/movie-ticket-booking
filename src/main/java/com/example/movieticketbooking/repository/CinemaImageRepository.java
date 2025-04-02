package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.CinemaImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaImageRepository extends JpaRepository<CinemaImageEntity, Integer> {
    List<CinemaImageEntity> findByCinemaId(Integer cinemaId);
}
