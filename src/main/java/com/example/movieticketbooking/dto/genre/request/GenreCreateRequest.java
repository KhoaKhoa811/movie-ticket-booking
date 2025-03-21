package com.example.movieticketbooking.dto.genre.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenreCreateRequest {
    @NotBlank(message = "Movie genre cannot be blank")
    private String name;
}
