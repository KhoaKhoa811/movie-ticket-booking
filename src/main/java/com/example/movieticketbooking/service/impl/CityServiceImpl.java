package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.city.response.CityResponse;
import com.example.movieticketbooking.dto.city.request.CityRequest;
import com.example.movieticketbooking.entity.CityEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.CityMapper;
import com.example.movieticketbooking.repository.CityRepository;
import com.example.movieticketbooking.service.CityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    @Transactional
    public CityResponse createCity(CityRequest cityRequest) {
        CityEntity cityEntity = cityMapper.toEntity(cityRequest);
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
    public CityResponse findOrCreateCity(CityRequest cityRequest) {
        if (cityRequest.getId() != null) {
            // Lấy city từ DB nếu có ID
            return this.getCityById(cityRequest.getId());
        }
            // Nếu city chưa có ID, kiểm tra tên đã tồn tại chưa
        return this.getCityByName(cityRequest.getName())
                .orElseGet(() -> this.createCity(cityRequest));
    }

    @Override
    public List<CityResponse> getAllCities() {
        List<CityEntity> cityEntities = cityRepository.findAll();
        return cityMapper.toResponseList(cityEntities);
    }

    @Override
    public void removeCity(Integer id) {
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.CITY_NOT_FOUND);
        }
        cityRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CityResponse updateCity(Integer id, CityRequest cityRequest) {
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.CITY_NOT_FOUND);
        }
        CityEntity cityEntity = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.CITY_NOT_FOUND));
        cityEntity.setName(cityRequest.getName());
        return cityMapper.toResponse(cityRepository.save(cityEntity));
    }


}
