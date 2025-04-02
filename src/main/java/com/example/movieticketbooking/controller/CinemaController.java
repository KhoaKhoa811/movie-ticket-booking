package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.cinema.request.CinemaCreateRequest;
import com.example.movieticketbooking.dto.cinema.request.CinemaUpdateRequest;
import com.example.movieticketbooking.dto.cinema.response.CinemaResponse;
import com.example.movieticketbooking.dto.cinemaImage.CinemaImageResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.CinemaImageService;
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
    private final CinemaImageService cinemaImageService;

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CinemaResponse>> getCinemaById(@PathVariable Integer id) {
        ApiResponse<CinemaResponse> cinemaResponse = ApiResponse.<CinemaResponse>builder()
                .code(Code.CINEMA_GET_ALL.getCode())
                .data(cinemaService.getCinemaById(id))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cinemaResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCinemaById(@PathVariable Integer id) {
        cinemaService.removeCinema(id);
        ApiResponse<?> cinemaResponse = ApiResponse.builder()
                .code(Code.CINEMA_DELETED.getCode())
                .message(Code.CINEMA_DELETED.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cinemaResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CinemaResponse>>> getAllCinema() {
        ApiResponse<List<CinemaResponse>> cinemaResponse = ApiResponse.<List<CinemaResponse>>builder()
                .code(Code.CINEMA_GET_ALL.getCode())
                .data(cinemaService.getAllCinema())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cinemaResponse);
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<ApiResponse<List<CinemaResponse>>> getCinemaByCityId(@PathVariable Integer id) {
        ApiResponse<List<CinemaResponse>> cinemaResponse = ApiResponse.<List<CinemaResponse>>builder()
                .code(Code.CINEMA_GET_ALL.getCode())
                .data(cinemaService.getCinemaByCityId(id))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cinemaResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CinemaResponse>> updateCinema(
            @PathVariable Integer id,
            @RequestBody CinemaUpdateRequest request) {
        ApiResponse<CinemaResponse> cinemaResponse = ApiResponse.<CinemaResponse>builder()
                .code(Code.CINEMA_UPDATED.getCode())
                .data(cinemaService.updateCinema(id, request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cinemaResponse);
    }

    @PostMapping(value = "{cinemaId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<CinemaImageResponse>>> uploadCinemaImages(
            @PathVariable Integer cinemaId,
            @RequestPart(value = "images") List<MultipartFile> images
    ) {
        ApiResponse<List<CinemaImageResponse>> cinemaImageResponse = ApiResponse.<List<CinemaImageResponse>>builder()
                .code(Code.CINEMA_IMAGES_CREATED.getCode())
                .data(cinemaImageService.createCinemaImage(cinemaId, images))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(cinemaImageResponse);
    }

    @DeleteMapping(value = "{cinemaId}/images/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCinemaImages(
            @PathVariable Integer cinemaId,
            @PathVariable Integer id) {
        cinemaImageService.removeCinemaImage(cinemaId, id);
        ApiResponse<?> cinemaImageResponse = ApiResponse.builder()
                .code(Code.CINEMA_IMAGES_DELETED.getCode())
                .message(Code.CINEMA_IMAGES_DELETED.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cinemaImageResponse);
    }

    @GetMapping("{cinemaId}/images")
    public ResponseEntity<ApiResponse<List<CinemaImageResponse>>> getCinemaImages(@PathVariable Integer cinemaId) {
        ApiResponse<List<CinemaImageResponse>> cinemaImageResponse = ApiResponse.<List<CinemaImageResponse>>builder()
                .code(Code.CINEMA_IMAGES_GET.getCode())
                .data(cinemaImageService.getCinemaImageByCinemaId(cinemaId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(cinemaImageResponse);
    }
}
