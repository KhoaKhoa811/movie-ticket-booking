package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.genre.request.GenreCreateRequest;
import com.example.movieticketbooking.dto.genre.response.GenreResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<ApiResponse<GenreResponse>> createGenre(@RequestBody @Valid GenreCreateRequest request) {
        ApiResponse<GenreResponse> genreResponse = ApiResponse.<GenreResponse>builder()
                .code(Code.GENRE_CREATED.getCode())
                .data(genreService.createGenre(request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(genreResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Integer id) {
        genreService.removeGenre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GenreResponse>>> getAllGenres() {
        ApiResponse<List<GenreResponse>> genreResponse = ApiResponse.<List<GenreResponse>>builder()
                .code(Code.GENRE_GET_ALL.getCode())
                .data(genreService.getAllGenre())
                .build();

        return ResponseEntity.ok(genreResponse);
    }
}
