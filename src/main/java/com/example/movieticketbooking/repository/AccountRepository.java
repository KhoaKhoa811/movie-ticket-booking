package com.example.movieticketbooking.repository;


import com.example.movieticketbooking.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
}
