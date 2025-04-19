package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    boolean existsByName(String name);
}
