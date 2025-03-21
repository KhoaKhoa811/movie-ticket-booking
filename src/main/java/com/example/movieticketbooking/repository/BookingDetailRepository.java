package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.BookingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailRepository extends JpaRepository<BookingDetailEntity, Integer> {
}
