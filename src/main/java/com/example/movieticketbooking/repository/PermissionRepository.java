package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {
}
