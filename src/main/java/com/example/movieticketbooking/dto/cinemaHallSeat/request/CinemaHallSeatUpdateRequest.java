package com.example.movieticketbooking.dto.cinemaHallSeat.request;

import com.example.movieticketbooking.enums.SeatType;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CinemaHallSeatUpdateRequest {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private SeatType type;
    private Boolean isActive;
}
