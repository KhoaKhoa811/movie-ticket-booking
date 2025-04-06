package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.seatTemplateDetail.SeatTemplateDetailResponse;
import com.example.movieticketbooking.entity.SeatTemplateDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatTemplateDetailMapper {
    SeatTemplateDetailResponse toResponse(SeatTemplateDetailEntity seatTemplateDetailEntity);
    List<SeatTemplateDetailResponse> toResponseList(List<SeatTemplateDetailEntity> seatTemplateDetailEntities);
}
