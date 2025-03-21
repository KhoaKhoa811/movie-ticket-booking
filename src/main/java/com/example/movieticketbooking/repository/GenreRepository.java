package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
}
