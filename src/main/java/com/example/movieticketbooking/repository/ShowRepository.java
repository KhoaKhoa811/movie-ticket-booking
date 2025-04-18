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
    @Query("SELECT s " +
            "FROM ShowEntity s " +
            "JOIN s.cinemaHall ch " +
            "WHERE ch.cinema.id = :cinemaId " +
            "AND s.movie.id = :movieId " +
            "ORDER BY s.startTime ASC")
    List<ShowEntity> findByMovieIdAndCinemaHallIds(@Param("movieId") Integer movieId,
                                                   @Param("cinemaId") Integer cinemaId);
    @Query("SELECT s " +
            "FROM ShowEntity s " +
            "JOIN s.cinemaHall ch " +
            "WHERE ch.cinema.id = :cinemaId " +
            "AND s.movie.id = :movieId " +
            "AND s.showDate = :showDate " +
            "ORDER BY s.startTime ASC")
    List<ShowEntity> findByMovieIdAndCinemaHallIdsAndShowDate(@Param("movieId") Integer movieId,
                                                              @Param("cinemaId") Integer cinemaId,
                                                              @Param("showDate") LocalDate showDate);

}
