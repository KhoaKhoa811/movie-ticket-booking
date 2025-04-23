package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.config.PaginationProperties;
import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.pagination.PaginationRequest;
import com.example.movieticketbooking.dto.ticket.response.TicketResponse;
import com.example.movieticketbooking.dto.ticket.response.TicketWithSeatResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.TicketService;
import com.example.movieticketbooking.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final PaginationProperties paginationProperties;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<TicketWithSeatResponse>>> getTicketsByShowId(
            @RequestParam("showId") Integer showId,
            @ModelAttribute PaginationRequest paginationRequest) {
        Pageable pageable = PaginationUtils.createPageable(paginationRequest, paginationProperties);
        ApiResponse<PagedResponse<TicketWithSeatResponse>> ticketResponse = ApiResponse.<PagedResponse<TicketWithSeatResponse>>builder()
                .code(Code.TICKET_GET_SUCCESS.getCode())
                .data(ticketService.getTicketsByShowId(showId, pageable))
                .build();
        return ResponseEntity.ok(ticketResponse);
    }

    @GetMapping("/show-and-seat")
    public ResponseEntity<ApiResponse<TicketResponse>> getTicketByShowIdAndSeatId(
            @RequestParam("showId") Integer showId,
            @RequestParam("seatId") Integer seatId) {
        ApiResponse<TicketResponse> ticketResponse = ApiResponse.<TicketResponse>builder()
                .code(Code.TICKET_GET_SUCCESS.getCode())
                .data(ticketService.getTicketByShowIdAndSeatId(showId, seatId))
                .build();
        return ResponseEntity.ok(ticketResponse);
    }
}
