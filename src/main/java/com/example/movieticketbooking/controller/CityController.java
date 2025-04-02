package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.city.request.CityRequest;
import com.example.movieticketbooking.dto.city.response.CityResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CityResponse>>> getAllCities() {
        ApiResponse<List<CityResponse>> cityResponse = ApiResponse.<List<CityResponse>>builder()
                .code(Code.CITY_GET_ALL.getCode())
                .data(cityService.getAllCities())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cityResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CityResponse>> createCity(@RequestBody CityRequest cityRequest) {
        ApiResponse<CityResponse> cityResponse = ApiResponse.<CityResponse>builder()
                .code(Code.CITY_CREATED.getCode())
                .data(cityService.createCity(cityRequest))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(cityResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CityResponse>> deleteCity(@PathVariable Integer id) {
        cityService.removeCity(id);
        ApiResponse<CityResponse> cityResponse = ApiResponse.<CityResponse>builder()
                .code(Code.CITY_DELETED.getCode())
                .message(Code.CITY_DELETED.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cityResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CityResponse>> updateCity(
            @PathVariable Integer id,
            @RequestBody CityRequest cityRequest) {
        ApiResponse<CityResponse> cityResponse = ApiResponse.<CityResponse>builder()
                .code(Code.CITY_UPDATED.getCode())
                .data(cityService.updateCity(id, cityRequest))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cityResponse);
    }
}
