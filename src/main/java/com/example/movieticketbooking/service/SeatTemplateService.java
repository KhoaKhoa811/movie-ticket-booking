package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.seatTemplate.SeatTemplateResponse;

public interface SeatTemplateService {
    SeatTemplateResponse getSeatTemplateById(Integer id);
}
