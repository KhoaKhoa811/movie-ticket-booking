package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.cinemaHall.request.CinemaHallCreateRequest;
import com.example.movieticketbooking.dto.cinemaHall.request.CinemaHallUpdateRequest;
import com.example.movieticketbooking.dto.cinemaHall.response.CinemaHallResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.CinemaHallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/halls")
@RequiredArgsConstructor
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;

    @PostMapping
    public ResponseEntity<ApiResponse<CinemaHallResponse>> createCinemaHall(
            @RequestBody CinemaHallCreateRequest cinemaHallCreateRequest
    ) {
        ApiResponse<CinemaHallResponse> cinemaHallResponse = ApiResponse.<CinemaHallResponse>builder()
                .code(Code.CINEMA_HALL_CREATED.getCode())
                .data(cinemaHallService.createCinemaHall(cinemaHallCreateRequest))
                .build();
        return ResponseEntity.ok(cinemaHallResponse);
    }

    @GetMapping("/cinemas/{cinemaId}")
    public ResponseEntity<ApiResponse<List<CinemaHallResponse>>> getCinemaHallsByCinemaId(@PathVariable Integer cinemaId) {
        ApiResponse<List<CinemaHallResponse>> cinemaHallResponse = ApiResponse.<List<CinemaHallResponse>>builder()
                .code(Code.CINEMA_HALL_GET_ALL.getCode())
                .data(cinemaHallService.getCinemaHallsByCinemaId(cinemaId))
                .build();
        return ResponseEntity.ok(cinemaHallResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCinemaHall(
            @PathVariable Integer id
    ) {
        cinemaHallService.removeCinemaHall(id);
        ApiResponse<?> cinemaHallResponse = ApiResponse.builder()
                .code(Code.CINEMA_HALL_DELETED.getCode())
                .message(Code.CINEMA_HALL_DELETED.getMessage())
                .build();
        return ResponseEntity.ok(cinemaHallResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CinemaHallResponse>> updateCinemaHall(
            @PathVariable Integer id,
            @RequestBody CinemaHallUpdateRequest cinemaHallUpdateRequest
    ) {
        ApiResponse<CinemaHallResponse> cinemaHallResponse = ApiResponse.<CinemaHallResponse>builder()
                .code(Code.CINEMA_HALL_UPDATED.getCode())
                .data(cinemaHallService.updateCinemaHall(id, cinemaHallUpdateRequest))
                .build();
        return ResponseEntity.ok(cinemaHallResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CinemaHallResponse>> getCinemaHallById(@PathVariable Integer id) {
        ApiResponse<CinemaHallResponse> cinemaHallResponse = ApiResponse.<CinemaHallResponse>builder()
                .code(Code.CINEMA_HALL_GET_ALL.getCode())
                .data(cinemaHallService.getCinemaById(id))
                .build();
        return ResponseEntity.ok(cinemaHallResponse);
    }
}
