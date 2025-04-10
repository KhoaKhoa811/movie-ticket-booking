package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    boolean existsByTitle(String title);
    @Query(value = "SELECT title FROM movie WHERE id=:id", nativeQuery = true)
    String findTitleById(@Param("id") Integer id);
    @Query("SELECT m FROM MovieEntity m WHERE m.releaseDate >= :releaseDate AND m.isActive = :isActive")
    Page<MovieEntity> findActiveMoviesToday(@Param("releaseDate") LocalDate releaseDate,
                                            @Param("isActive") Boolean isActive,
                                            Pageable pageable);
}
