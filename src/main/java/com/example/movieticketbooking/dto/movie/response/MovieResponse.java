package com.example.movieticketbooking.dto.movie.response;

import com.example.movieticketbooking.dto.genre.response.GenreResponse;
import com.example.movieticketbooking.enums.MovieType;
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
public class MovieResponse {
    private Integer id;
    private String title;
    private String imagePath;
    private String description;
    private String director;
    private Integer durationInMinutes;
    private String releaseDate;
    private String language;
    private String country;
    private String startDate;
    private String endDate;
    private Boolean isActive;
    private MovieType movieType;
    private List<GenreResponse> genres;
}
