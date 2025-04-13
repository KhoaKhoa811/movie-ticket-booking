package com.example.movieticketbooking.dto.show.response;

import com.example.movieticketbooking.enums.ShowType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowBasicResponse {
    private Integer id;
    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isActive;
    @Enumerated(EnumType.STRING)
    private ShowType type;
}
