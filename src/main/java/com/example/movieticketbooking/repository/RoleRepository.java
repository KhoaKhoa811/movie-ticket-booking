package com.example.movieticketbooking.repository;

import com.example.movieticketbooking.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    boolean existsByName(String name);
    @Query("SELECT r FROM RoleEntity r JOIN r.accounts a WHERE a.id = :accountId")
    List<RoleEntity> findByAccountId(@Param("accountId") Integer accountId);
}
