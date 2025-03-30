package com.example.movieticketbooking.dto.city.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityCreateRequest {
    private Integer id;
    @NotBlank(message = "CINEMA_ADDRESS_INVALID")
    private String name;
}
