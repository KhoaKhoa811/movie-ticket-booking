package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.dto.cinema.response.CinemaCityResponse;
import com.example.movieticketbooking.dto.cinema.response.CityResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CinemaRepository extends JpaRepository<CinemaEntity, Integer> {
    boolean existsByName(String name);
    @Query(value = "SELECT DISTINCT city FROM cinema", nativeQuery = true)
    List<CityResponse> findAllCities();
    boolean existsByCity(String city);
    @Query(value = "SELECT id, name FROM cinema WHERE city = :city", nativeQuery = true)
    List<CinemaCityResponse> getCinemasByCity(@Param("city") String city);
}
