package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.cinema.request.CinemaCreateRequest;
import com.example.movieticketbooking.dto.cinema.response.CinemaCityResponse;
import com.example.movieticketbooking.dto.cinema.response.CityResponse;
import com.example.movieticketbooking.dto.cinema.response.CinemaResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.CinemaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/cinemas")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CinemaResponse>> createCinema(
            @RequestPart("cinema") String cinemaJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CinemaCreateRequest cinemaRequest = objectMapper.readValue(cinemaJson, CinemaCreateRequest.class);
        ApiResponse<CinemaResponse> cinemaResponse = ApiResponse.<CinemaResponse>builder()
                .code(Code.CINEMA_CREATED.getCode())
                .data(cinemaService.createCinema(cinemaRequest, images))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(cinemaResponse);
    }

    @GetMapping("/cities")
    public ResponseEntity<ApiResponse<List<CityResponse>>> getAllCinemaCity() {
        ApiResponse<List<CityResponse>> cityResponse = ApiResponse.<List<CityResponse>>builder()
                .code(Code.CITY_GET_ALL.getCode())
                .data(cinemaService.getAllCity())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cityResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CinemaCityResponse>>> getCinemaByCity(@RequestParam String city) {
        ApiResponse<List<CinemaCityResponse>> cinemaResponse = ApiResponse.<List<CinemaCityResponse>>builder()
                .code(Code.CINEMA_GET_ALL.getCode())
                .data(cinemaService.getCinemaByCity(city))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cinemaResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CinemaResponse>> getCinemaById(@PathVariable Integer id) {
        ApiResponse<CinemaResponse> cinemaResponse = ApiResponse.<CinemaResponse>builder()
                .code(Code.CINEMA_GET_ALL.getCode())
                .data(cinemaService.getCinemaById(id))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cinemaResponse);
    }
}
