package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.movie.request.MovieCreateRequest;
import com.example.movieticketbooking.dto.movie.response.MovieResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(
            @RequestPart("movie") String movieJson,
            @RequestPart(value = "image") MultipartFile movieImage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MovieCreateRequest movieCreateRequest = objectMapper.readValue(movieJson, MovieCreateRequest.class);
        ApiResponse<MovieResponse> movieResponse = ApiResponse.<MovieResponse>builder()
                .code(Code.MOVIE_CREATED.getCode())
                .data(movieService.createMovie(movieCreateRequest, movieImage))
                .build();
        return ResponseEntity.ok(movieResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovie(@PathVariable("id") Integer id) {
        ApiResponse<MovieResponse> movieResponse = ApiResponse.<MovieResponse>builder()
                .code(Code.MOVIE_GET_ALL.getCode())
                .data(movieService.getMovieById(id))
                .build();
        return ResponseEntity.ok(movieResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteMovieById(@PathVariable("id") Integer id) {
        movieService.removeMovieById(id);
        ApiResponse<?> movieResponse = ApiResponse.builder()
                .code(Code.MOVIE_DELETED.getCode())
                .message(Code.MOVIE_DELETED.getMessage())
                .build();
        return ResponseEntity.ok(movieResponse);
    }
}
