package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.ticket.response.TicketResponse;
import com.example.movieticketbooking.dto.ticket.response.TicketWithSeatResponse;
import com.example.movieticketbooking.entity.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CinemaHallSeatMapper.class})
public interface TicketMapper {
    // convert ticket entity to ticket response
    TicketWithSeatResponse toWithSeatResponse(TicketEntity ticketEntity);
    List<TicketWithSeatResponse> toWithSeatResponseList(List<TicketEntity> ticketEntity);
    TicketResponse toResponse(TicketEntity ticketEntity);
    List<TicketResponse> toResponseList(List<TicketEntity> ticketEntity);
}
