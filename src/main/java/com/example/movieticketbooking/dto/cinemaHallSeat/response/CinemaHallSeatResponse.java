package com.example.movieticketbooking.dto.cinemaHallSeat.response;

import com.example.movieticketbooking.enums.SeatType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaHallSeatResponse {
    private Integer id;
    private Character seatRow;
    private Integer seatCol;
    @Enumerated(EnumType.STRING)
    private SeatType type;
    private Boolean isActive;
}
