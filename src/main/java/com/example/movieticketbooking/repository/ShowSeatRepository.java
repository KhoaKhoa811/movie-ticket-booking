package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.ShowSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowSeatRepository extends JpaRepository<ShowSeatEntity, Integer> {
}
