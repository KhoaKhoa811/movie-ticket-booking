package com.example.movieticketbooking.dto.booking.response;

import com.example.movieticketbooking.dto.ticket.response.TicketResponse;
import com.example.movieticketbooking.enums.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Integer id;
    private Integer numberOfSeats;
    private Double totalPrice;
    private String createdAt;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private List<TicketResponse> tickets;
}
