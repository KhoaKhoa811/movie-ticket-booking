package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.cinemaHall.request.CinemaHallCreateRequest;
import com.example.movieticketbooking.dto.cinemaHall.response.CinemaHallResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.CinemaHallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cinemas/{cinemaId}/halls")
@RequiredArgsConstructor
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;

    @PostMapping
    public ResponseEntity<ApiResponse<CinemaHallResponse>> createCinemaHall(
            @PathVariable Integer cinemaId,
            @RequestBody CinemaHallCreateRequest cinemaHallCreateRequest
    ) {
        ApiResponse<CinemaHallResponse> cinemaHallResponse = ApiResponse.<CinemaHallResponse>builder()
                .code(Code.CINEMA_HALL_CREATED.getCode())
                .data(cinemaHallService.createCinemaHall(cinemaId, cinemaHallCreateRequest))
                .build();
        return ResponseEntity.ok(cinemaHallResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CinemaHallResponse>>> getCinemaHallsByCinemaId(@PathVariable Integer cinemaId) {
        ApiResponse<List<CinemaHallResponse>> cinemaHallResponse = ApiResponse.<List<CinemaHallResponse>>builder()
                .code(Code.CINEMA_HALL_GET_ALL.getCode())
                .data(cinemaHallService.getCinemaHallsByCinemaId(cinemaId))
                .build();
        return ResponseEntity.ok(cinemaHallResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCinemaHall(
            @PathVariable Integer cinemaId,
            @PathVariable Integer id
    ) {
        cinemaHallService.removeCinemaHall(cinemaId, id);
        ApiResponse<?> cinemaHallResponse = ApiResponse.builder()
                .code(Code.CINEMA_HALL_DELETED.getCode())
                .message(Code.CINEMA_HALL_DELETED.getMessage())
                .build();
        return ResponseEntity.ok(cinemaHallResponse);
    }
}
