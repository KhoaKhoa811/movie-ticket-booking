package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.ticket.response.TicketResponse;
import com.example.movieticketbooking.dto.ticket.response.TicketWithSeatResponse;
import com.example.movieticketbooking.entity.ShowEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {
    void generateTicket(ShowEntity showEntity);
    PagedResponse<TicketWithSeatResponse> getTicketsByShowId(Integer showId, Pageable pageable);
    TicketResponse getTicketByShowIdAndSeatId(Integer showId, Integer seatId);
}
