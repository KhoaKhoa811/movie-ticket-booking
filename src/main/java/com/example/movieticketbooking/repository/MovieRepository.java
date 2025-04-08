package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    boolean existsByTitle(String title);
    @Query(value = "SELECT title FROM movie WHERE id=:id", nativeQuery = true)
    String findTitleById(@Param("id") Integer id);
}
