package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
    boolean existsByShowId(Integer attr0);
    Page<TicketEntity> findByShowId(Integer showId, Pageable pageable);
    @Query("SELECT t FROM TicketEntity t WHERE t.show.id = :showId AND t.seat.id = :seatId")
    Optional<TicketEntity> findByShowIdAndSeatId(@Param("showId") Integer showId, @Param("seatId") Integer seatId);
}
