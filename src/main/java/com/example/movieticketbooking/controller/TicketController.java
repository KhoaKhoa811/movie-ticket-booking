package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.ticket.response.TicketWithSeatResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TicketWithSeatResponse>>> getTicketsByShowId(@RequestParam("showId") Integer showId) {
        ApiResponse<List<TicketWithSeatResponse>> ticketResponse = ApiResponse.<List<TicketWithSeatResponse>>builder()
                .code(Code.TICKET_GET_SUCCESS.getCode())
                .data(ticketService.getTicketsByShowId(showId))
                .build();
        return ResponseEntity.ok(ticketResponse);
    }
}
