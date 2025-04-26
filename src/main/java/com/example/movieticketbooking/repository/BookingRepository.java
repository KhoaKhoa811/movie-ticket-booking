package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.BookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
    Page<BookingEntity> findByAccountEmail(String accountEmail, Pageable pageable);
}
