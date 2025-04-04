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
    @Digits(integer = 3, fraction = 0, message = "CINEMA_HALL_ROW_COUNT_INVALID")
    private Integer rowCount;
    @Digits(integer = 3, fraction = 0, message = "CINEMA_HALL_COLUMN_COUNT_INVALID")
    private Integer columnCount;
    private Boolean isActive;
    private Integer cinemaId;
}
