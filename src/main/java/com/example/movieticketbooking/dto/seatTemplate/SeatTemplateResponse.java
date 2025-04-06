package com.example.movieticketbooking.dto.seatTemplate;

import com.example.movieticketbooking.dto.seatTemplateDetail.SeatTemplateDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatTemplateResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer rowCount;
    private Integer columnCount;
    private List<SeatTemplateDetailResponse> details;
}
