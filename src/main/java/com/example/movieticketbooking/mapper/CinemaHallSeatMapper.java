package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.cinemaHallSeat.request.CinemaHallSeatUpdateRequest;
import com.example.movieticketbooking.dto.cinemaHallSeat.response.CinemaHallSeatResponse;
import com.example.movieticketbooking.entity.CinemaHallSeatEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CinemaHallSeatMapper {
    CinemaHallSeatResponse toResponse(CinemaHallSeatEntity cinemaHallSeatEntity);
    List<CinemaHallSeatResponse> toResponseList(List<CinemaHallSeatEntity> cinemaHallSeatEntities);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateSeatFromUpdateRequest(@MappingTarget CinemaHallSeatEntity cinemaHallSeatEntity, CinemaHallSeatUpdateRequest cinemaHallSeatUpdateRequest);
}
