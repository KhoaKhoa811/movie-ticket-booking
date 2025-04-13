package com.example.movieticketbooking.dto.show.response;

import com.example.movieticketbooking.enums.ShowType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowResponse {
    private Integer id;
    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    private ShowType type;
    private Boolean isActive;
    private Integer movieId;
    private String movieTitle;
    private Integer cinemaHallId;
    private String cinemaHallName;

}