package com.example.movieticketbooking.dto.show.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieAndCinemaRequest {
    private Integer movieId;
    private Integer cinemaId;
}
