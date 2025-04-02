package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.CinemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CinemaRepository extends JpaRepository<CinemaEntity, Integer> {
    boolean existsByName(String name);
    @Query(value = "SELECT name FROM cinema WHERE id = :id", nativeQuery = true)
    String findNameById(@Param("id") Integer id);
    List<CinemaEntity> findByCityId(Integer id);
}
