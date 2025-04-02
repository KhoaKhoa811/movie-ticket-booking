package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<ShowEntity, Integer> {
}
