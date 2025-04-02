package com.example.movieticketbooking.dto.cinema.request;

import com.example.movieticketbooking.dto.city.request.CityRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CinemaUpdateRequest {
    private String name;
    private String address;
    private String street;
    private String ward;
    private String district;
    @Pattern(regexp = "^\\d{10}$", message = "CINEMA_PHONE_INVALID")
    private String phoneNumber;
    private CityRequest city;
}
