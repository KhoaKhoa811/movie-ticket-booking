package com.example.movieticketbooking.dto.seatTemplateDetail;

import com.example.movieticketbooking.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatTemplateDetailResponse {
    private Integer id;
    private Character seatRow;
    private Integer seatColumn;
    private SeatType type;
    private Boolean isActive;
}
