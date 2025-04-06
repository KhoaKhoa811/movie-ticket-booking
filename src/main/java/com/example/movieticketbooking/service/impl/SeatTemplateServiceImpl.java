package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.seatTemplate.SeatTemplateResponse;
import com.example.movieticketbooking.entity.SeatTemplateEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.SeatTemplateMapper;
import com.example.movieticketbooking.repository.SeatTemplateRepository;
import com.example.movieticketbooking.service.SeatTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatTemplateServiceImpl implements SeatTemplateService {
    private final SeatTemplateRepository seatTemplateRepository;
    private final SeatTemplateMapper seatTemplateMapper;

    @Override
    public SeatTemplateResponse getSeatTemplateById(Integer id) {
        SeatTemplateEntity seatTemplateEntity = seatTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.SEAT_TEMPLATE_NOT_FOUND));
        return seatTemplateMapper.toResponse(seatTemplateEntity);
    }
}
