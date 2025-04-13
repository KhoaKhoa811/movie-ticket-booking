package com.example.movieticketbooking.controller;

import com.cloudinary.Api;
import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.show.request.ShowCreateRequest;
import com.example.movieticketbooking.dto.show.response.ShowResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
