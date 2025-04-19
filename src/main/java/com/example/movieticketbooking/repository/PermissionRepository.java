package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.PermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {
    @Query("SELECT p FROM PermissionEntity p JOIN p.roles r WHERE r.id = :roleId")
    Page<PermissionEntity> findAllByRoleId(@Param("roleId") Integer roleId, Pageable pageable);
}
