package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.city.response.CityResponse;
import com.example.movieticketbooking.dto.city.request.CityCreateRequest;
import com.example.movieticketbooking.entity.CityEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.CityMapper;
import com.example.movieticketbooking.repository.CityRepository;
import com.example.movieticketbooking.service.CityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    @Transactional
    public CityResponse createCity(CityCreateRequest cityCreateRequest) {
        CityEntity cityEntity = cityMapper.toEntity(cityCreateRequest);
        return cityMapper.toResponse(cityRepository.save(cityEntity));
    }

    @Override
    public CityResponse getCityById(Integer id) {
        CityEntity cityEntity =  cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.CITY_NOT_FOUND));
        return cityMapper.toResponse(cityEntity);
    }

    @Override
    public Optional<CityResponse> getCityByName(String name) {
        return cityRepository.findByName(name).map(cityMapper::toResponse);
    }

    @Override
    @Transactional
    public CityResponse findOrCreateCity(CityCreateRequest cityCreateRequest) {
        if (cityCreateRequest.getId() != null) {
            // Lấy city từ DB nếu có ID
            return this.getCityById(cityCreateRequest.getId());
        }
            // Nếu city chưa có ID, kiểm tra tên đã tồn tại chưa
        return this.getCityByName(cityCreateRequest.getName())
                .orElseGet(() -> this.createCity(cityCreateRequest));
    }


}
