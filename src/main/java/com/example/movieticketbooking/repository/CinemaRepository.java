package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.CinemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<CinemaEntity, Integer> {
}
