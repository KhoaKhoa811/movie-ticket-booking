package com.example.movieticketbooking.dto.cinemaImage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CinemaImageResponse {
    private Integer id;
    private String imageUrl;
    private String imageId;
}
