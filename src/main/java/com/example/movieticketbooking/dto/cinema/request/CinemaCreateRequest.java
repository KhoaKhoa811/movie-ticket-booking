package com.example.movieticketbooking.dto.cinema.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaCreateRequest {
    @NotBlank(message = "CINEMA_NAME_INVALID")
    private String name;
    @NotBlank(message = "CINEMA_ADDRESS_INVALID")
    private String address;
    @NotBlank(message = "CINEMA_ADDRESS_INVALID")
    private String street;
    @NotBlank(message = "CINEMA_ADDRESS_INVALID")
    private String ward;
    @NotBlank(message = "CINEMA_ADDRESS_INVALID")
    private String district;
    @NotBlank(message = "CINEMA_ADDRESS_INVALID")
    private String city;
    @NotBlank(message = "CINEMA_PHONE_INVALID")
    @Pattern(regexp = "^\\d{10}$", message = "CINEMA_PHONE_INVALID")
    private String phoneNumber;
}
