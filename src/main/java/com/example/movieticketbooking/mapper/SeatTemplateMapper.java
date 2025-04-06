package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.seatTemplate.SeatTemplateResponse;
import com.example.movieticketbooking.entity.SeatTemplateEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = SeatTemplateDetailMapper.class)
public interface SeatTemplateMapper {
    SeatTemplateResponse toResponse(SeatTemplateEntity seatTemplateEntity);
    List<SeatTemplateResponse> toResponseList(List<SeatTemplateEntity> seatTemplateEntities);
}
