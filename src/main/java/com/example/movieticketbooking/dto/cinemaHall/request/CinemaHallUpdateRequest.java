package com.example.movieticketbooking.dto.cinemaHall.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CinemaHallUpdateRequest {
    private String name;
    private Boolean isActive;
    private Integer cinemaId;
}
