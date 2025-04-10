package com.example.movieticketbooking.dto.movie.request;

import com.example.movieticketbooking.enums.MovieType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieUpdateRequest {
    private String title;
    private String description;
    private String director;
    private String durationInMinutes;
    private String releaseDate;
    private String language;
    private String country;
    private Boolean isActive;
    @Enumerated(EnumType.STRING)
    private MovieType movieType;
    private List<Integer> genreIds;
}
