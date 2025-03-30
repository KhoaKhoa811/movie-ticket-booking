package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.city.response.CityResponse;
import com.example.movieticketbooking.dto.city.request.CityRequest;

import java.util.List;
import java.util.Optional;

public interface CityService {
    CityResponse createCity(CityRequest cityRequest);
    CityResponse getCityById(Integer id);
    Optional<CityResponse> getCityByName(String name);
    CityResponse findOrCreateCity(CityRequest cityRequest);
    List<CityResponse> getAllCities();
    void removeCity(Integer id);
    CityResponse updateCity(Integer id, CityRequest cityRequest);
}
