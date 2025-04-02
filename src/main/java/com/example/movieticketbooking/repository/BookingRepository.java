package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
}
