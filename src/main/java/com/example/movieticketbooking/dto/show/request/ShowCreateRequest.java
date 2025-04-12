package com.example.movieticketbooking.dto.show.request;

import com.example.movieticketbooking.enums.ShowType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowCreateRequest {
    private LocalDate showDate;
    @Enumerated(EnumType.STRING)
    private ShowType type;
    private Boolean isActive;
    private Integer movieId;
    private Integer cinemaHallId;
    private List<LocalTime> startTimeList;
}
