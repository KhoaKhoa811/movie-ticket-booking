package com.example.movieticketbooking.dto.ticket.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketUpdateRequest {
    private Double price;
    private Boolean isBooked;
    private String ticketCode;
    private String issuedAt;
}
