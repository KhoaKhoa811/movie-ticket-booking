package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.cinemaHallSeat.request.CinemaHallSeatUpdateRequest;
import com.example.movieticketbooking.dto.cinemaHallSeat.response.CinemaHallSeatResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.CinemaHallSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/halls/{cinemaHallId}/seats")
@RequiredArgsConstructor
public class CinemaHallSeatController {
    private final CinemaHallSeatService cinemaHallSeatService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CinemaHallSeatResponse>>> getHallSeat(@PathVariable Integer cinemaHallId) {
        ApiResponse<List<CinemaHallSeatResponse>> hallSeatResponse = ApiResponse.<List<CinemaHallSeatResponse>>builder()
                .code(Code.SEAT_GET_ALL.getCode())
                .data(cinemaHallSeatService.getHallSeats(cinemaHallId))
                .build();
        return ResponseEntity.ok(hallSeatResponse);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<List<CinemaHallSeatResponse>>> updateListSeats(
            @PathVariable Integer cinemaHallId,
            @RequestBody List<CinemaHallSeatUpdateRequest> cinemaHallSeatUpdateRequests)
    {
        ApiResponse<List<CinemaHallSeatResponse>> hallSeatResponse = ApiResponse.<List<CinemaHallSeatResponse>>builder()
                .code(Code.SEAT_UPDATED.getCode())
                .data(cinemaHallSeatService.updateListSeats(cinemaHallId, cinemaHallSeatUpdateRequests))
                .build();
        return ResponseEntity.ok(hallSeatResponse);
    }
}
