package com.example.movieticketbooking.dto.cinemaHall.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaHallCreateRequest {
    @NotBlank(message = "CINEMA_HALL_NAME_INVALID")
    private String name;
    @NotNull(message = "CINEMA_HALL_ACTIVE_INVALID")
    private Boolean isActive;
    @NotNull(message = "CINEMA_INVALID")
    private Integer cinemaId;
    @NotNull(message = "SEAT_TEMPLATE_INVALID")
    private Integer seatTemplateId;
}


