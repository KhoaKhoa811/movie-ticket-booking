package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.SeatTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatTemplateRepository extends JpaRepository<SeatTemplateEntity, Integer> {
}
