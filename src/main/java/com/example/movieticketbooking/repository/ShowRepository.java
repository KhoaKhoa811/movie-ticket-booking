package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<ShowEntity, Integer> {
    List<ShowEntity> findByCinemaHallIdAndShowDate(Integer cinemaHallId, LocalDate showDate);
}
