package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Integer> {
    Optional<CityEntity> findByName(String name);
}
