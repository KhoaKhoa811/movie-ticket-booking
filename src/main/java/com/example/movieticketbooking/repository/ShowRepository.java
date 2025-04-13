package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<ShowEntity, Integer> {
    List<ShowEntity> findByCinemaHallIdAndShowDate(Integer cinemaHallId, LocalDate showDate);
    boolean existsByMovieId(Integer movieId);
    @Query("SELECT s FROM ShowEntity s WHERE s.movie.id = :movieId AND s.cinemaHall.id IN :cinemaHallIds")
    List<ShowEntity> findByMovieIdAndCinemaHallIds(@Param("movieId") Integer movieId,
                                                   @Param("cinemaHallIds") List<Integer> cinemaHallIds);
}
