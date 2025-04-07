package com.example.movieticketbooking.dto.movie.request;

import com.example.movieticketbooking.enums.MovieType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieCreateRequest {
    @NotBlank(message = "MOVIE_TITLE_INVALID")
    private String title;
    @NotBlank(message = "MOVIE_DESCRIPTION_INVALID")
    private String description;
    private String director;
    @NotNull(message = "MOVIE_DURATION_INVALID")
    @Min(value = 1, message = "MOVIE_DURATION_INVALID")
    private Integer durationInMinutes;
    @NotNull(message = "MOVIE_RELEASE_DATE_INVALID")
    @FutureOrPresent(message = "MOVIE_RELEASE_DATE_INVALID")
    private String releaseDate;
    private String language;
    private String country;
    @NotNull(message = "MOVIE_TYPE_INVALID")
    @Enumerated(EnumType.STRING)
    private MovieType movieType;
    @NotNull(message = "MOVIE_GENRE_INVALID")
    private List<Integer> genreIds;
}
