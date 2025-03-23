package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.genre.request.GenreCreateRequest;
import com.example.movieticketbooking.dto.genre.response.GenreResponse;
import com.example.movieticketbooking.enums.ErrorCode;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<ApiResponse<GenreResponse>> createGenre(@RequestBody @Valid GenreCreateRequest request) {
        ApiResponse<GenreResponse> genreResponse = ApiResponse.<GenreResponse>builder()
                .code(100)
                .data(genreService.createGenre(request))
                .build();
        return ResponseEntity.ok(genreResponse);
    }
}
