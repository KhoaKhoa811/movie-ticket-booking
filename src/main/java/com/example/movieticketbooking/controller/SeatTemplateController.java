package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.seatTemplate.SeatTemplateResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.SeatTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/seat-templates")
@RequiredArgsConstructor
public class SeatTemplateController {
    private final SeatTemplateService seatTemplateService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeatTemplateResponse>> getSeatTemplateById(@PathVariable Integer id){
        ApiResponse<SeatTemplateResponse> seatTemplateDetail = ApiResponse.<SeatTemplateResponse>builder()
                .code(Code.SEAT_TEMPLATE_GET_ALL.getCode())
                .data(seatTemplateService.getSeatTemplateById(id))
                .build();
        return ResponseEntity.ok(seatTemplateDetail);
    }
}
