package com.example.movieticketbooking.dto.cinemaImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CinemaImageResponse {
    private Integer id;
    private String imageUrl;
    private String imageId;
}
