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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleShowUpdateRequest {
    private Integer id;
    private LocalDate showDate;
    @Enumerated(EnumType.STRING)
    private ShowType type;
    private Boolean isActive;
    private LocalTime startTime;
}
