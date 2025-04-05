package com.example.movieticketbooking.dto.cinemaHall.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaHallResponse {
    private Integer id;
    private String name;
    private Boolean isActive;
    private Integer cinemaId;
}
