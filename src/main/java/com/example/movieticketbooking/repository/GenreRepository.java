package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
    boolean existsByName(String name);
    @Query(value = "SELECT id, name FROM genre", nativeQuery = true)
    List<GenreEntity> findAllGenre();
}
