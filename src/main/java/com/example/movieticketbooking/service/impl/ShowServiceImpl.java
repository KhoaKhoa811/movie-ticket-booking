package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.repository.ShowRepository;
import com.example.movieticketbooking.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;
}
