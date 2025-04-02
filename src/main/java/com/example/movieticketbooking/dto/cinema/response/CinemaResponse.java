package com.example.movieticketbooking.dto.cinema.response;

import com.example.movieticketbooking.dto.cinemaImage.CinemaImageResponse;
import com.example.movieticketbooking.dto.city.response.CityResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaResponse {
    private Integer id;
    private String name;
    private String address;
    private String street;
    private String ward;
    private String district;
    private String phoneNumber;
    private List<CinemaImageResponse> images;
    private CityResponse city;
}
