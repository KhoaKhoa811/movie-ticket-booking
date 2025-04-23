package com.example.movieticketbooking.dto.ticket.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Integer id;
    private Double price;
    private Boolean isBooked;
    private String ticketCode;
}
