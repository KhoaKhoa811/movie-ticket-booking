package com.example.movieticketbooking.controller;

import com.cloudinary.Api;
import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.show.request.MovieAndCinemaRequest;
import com.example.movieticketbooking.dto.show.request.ShowCreateRequest;
import com.example.movieticketbooking.dto.show.response.ShowBasicResponse;
import com.example.movieticketbooking.dto.show.response.ShowResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/shows")
@RequiredArgsConstructor
public class ShowController {
    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<ShowResponse>>> createShow(@RequestBody ShowCreateRequest request) {
        ApiResponse<List<ShowResponse>> showResponse = ApiResponse.<List<ShowResponse>>builder()
                .code(Code.SHOWS_CREATED.getCode())
                .data(showService.createShows(request))
                .build();
        return ResponseEntity.ok(showResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowBasicResponse>>> getShowsByMovieIdAndCinemaId(
            @ModelAttribute MovieAndCinemaRequest movieAndCinemaRequest
    ) {
        ApiResponse<List<ShowBasicResponse>> showResponse = ApiResponse.<List<ShowBasicResponse>>builder()
                .code(Code.SHOWS_GET.getCode())
                .data(showService.getShowsByMovieIdAndCinemaId(movieAndCinemaRequest))
                .build();
        return ResponseEntity.ok(showResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowBasicResponse>>> getShowsByMovieIdAndCinemaIdAndShowDate(
            @ModelAttribute MovieAndCinemaRequest movieAndCinemaRequest,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate showDate
    ) {
        ApiResponse<List<ShowBasicResponse>> showResponse = ApiResponse.<List<ShowBasicResponse>>builder()
                .code(Code.SHOWS_GET.getCode())
                .data(showService.getShowsByMovieIdAndCinemaIdAndShowDate(movieAndCinemaRequest, showDate))
                .build();
        return ResponseEntity.ok(showResponse);
    }


}
