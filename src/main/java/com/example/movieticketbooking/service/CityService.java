package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.city.response.CityResponse;
import com.example.movieticketbooking.dto.city.request.CityCreateRequest;
import com.example.movieticketbooking.entity.CityEntity;

import java.util.Optional;

public interface CityService {
    CityResponse createCity(CityCreateRequest cityCreateRequest);
    CityResponse getCityById(Integer id);
    Optional<CityResponse> getCityByName(String name);
    CityResponse findOrCreateCity(CityCreateRequest cityCreateRequest);
}
