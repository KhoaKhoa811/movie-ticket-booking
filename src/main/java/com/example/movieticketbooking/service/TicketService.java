package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.ticket.response.TicketWithSeatResponse;
import com.example.movieticketbooking.entity.ShowEntity;

import java.util.List;

public interface TicketService {
    void generateTicket(ShowEntity showEntity);
    List<TicketWithSeatResponse> getTicketsByShowId(Integer showId);
}
