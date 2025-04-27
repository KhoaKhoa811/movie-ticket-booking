package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.booking.response.BookingResponse;
import com.example.movieticketbooking.entity.BookingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TicketMapper.class})
public interface BookingMapper {
    BookingResponse toResponse(BookingEntity bookingEntity);
}
