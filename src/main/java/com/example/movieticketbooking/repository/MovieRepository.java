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
    // find all the active movie showing at that date
    @Query("SELECT m FROM MovieEntity m " +
            "WHERE m.startDate <= :date " +
            "AND m.endDate >= :date " +
            "AND m.isActive = :isActive")
    Page<MovieEntity> findActiveAvailableMoviesByDate(@Param("date") LocalDate date,
                                             @Param("isActive")Boolean isActive,
                                             Pageable pageable);
    // find all the active movie going to showing from that date
    @Query("SELECT m FROM MovieEntity m " +
            "WHERE m.startDate >= :date " +
            "AND m.isActive = :isActive")
    Page<MovieEntity> findActiveUpcomingMoviesByDate(@Param("date") LocalDate date,
                                                     @Param("isActive")Boolean isActive,
                                                     Pageable pageable);
}
