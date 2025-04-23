package com.example.movieticketbooking.dto.ticket.response;

import com.example.movieticketbooking.dto.cinemaHallSeat.response.CinemaHallSeatResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketWithSeatResponse {
    private Integer id;
    private Double price;
    private Boolean isBooked;
    private String ticketCode;
    private CinemaHallSeatResponse seat;
}
