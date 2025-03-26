package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.cinema.request.CinemaCreateRequest;
import com.example.movieticketbooking.dto.cinema.response.CinemaResponse;
import com.example.movieticketbooking.dto.cinemaImage.CinemaImageResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaImageEntity;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.mapper.CinemaMapper;
import com.example.movieticketbooking.repository.CinemaRepository;
import com.example.movieticketbooking.service.impl.CinemaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CinemaServiceTest {
    @Mock
    private CinemaRepository cinemaRepository;

    @Mock
    private CinemaMapper cinemaMapper;

    @Mock
    private CinemaImageService cinemaImageService;

    @InjectMocks
    private CinemaServiceImpl cinemaService;

    @Test
    void createCinema_ShouldCreateSuccessfully() {
        // given
        CinemaCreateRequest request = new CinemaCreateRequest(
                "cinema", "address", "street", "ward", "district", "city", "0933333123"
        );

        CinemaEntity cinemaEntity = new CinemaEntity();
        cinemaEntity.setId(1);
        cinemaEntity.setName(request.getName());
        cinemaEntity.setAddress(request.getAddress());
        cinemaEntity.setStreet(request.getStreet());
        cinemaEntity.setWard(request.getWard());
        cinemaEntity.setDistrict(request.getDistrict());
        cinemaEntity.setCity(request.getCity());
        cinemaEntity.setPhoneNumber(request.getPhoneNumber());

        List<CinemaImageEntity> images = List.of(new CinemaImageEntity(1, "url1", "img1", cinemaEntity));
        cinemaEntity.setCinemaImages(images);

        List<CinemaImageResponse> imageResponses = List.of(new CinemaImageResponse(1, "url1", "img1"));

        CinemaResponse expectedResponse = new CinemaResponse(
                cinemaEntity.getId(),
                cinemaEntity.getName(),
                cinemaEntity.getAddress(),
                cinemaEntity.getStreet(),
                cinemaEntity.getWard(),
                cinemaEntity.getDistrict(),
                cinemaEntity.getCity(),
                cinemaEntity.getPhoneNumber(),
                imageResponses
        );

        when(cinemaRepository.existsByName(request.getName())).thenReturn(false);
        when(cinemaMapper.toEntity(request)).thenReturn(cinemaEntity);
        when(cinemaRepository.save(cinemaEntity)).thenReturn(cinemaEntity);
        when(cinemaMapper.toResponse(cinemaEntity)).thenReturn(expectedResponse);

        // When
        CinemaResponse response = cinemaService.createCinema(request, Collections.emptyList());

        // Then
        assertNotNull(response);
        assertNotNull(response.getId(), "ID should not be null");
        assertNotNull(response.getName(), "Name should not be null");
        assertNotNull(response.getAddress(), "Address should not be null");
        assertNotNull(response.getStreet(), "Street should not be null");
        assertNotNull(response.getWard(), "Ward should not be null");
        assertNotNull(response.getDistrict(), "District should not be null");
        assertNotNull(response.getCity(), "City should not be null");
        assertNotNull(response.getPhoneNumber(), "Phone number should not be null");
        assertNotNull(response.getImages(), "Images should not be null");
        assertFalse(response.getImages().isEmpty(), "Images list should not be empty");

        assertEquals(expectedResponse, response);
        verify(cinemaRepository).save(cinemaEntity);
    }


    @Test
    void createCinema_WhenNameExists_ShouldThrowException() {
        // Given
        CinemaCreateRequest request = new CinemaCreateRequest(
                "Cinema A", "Address", "Street", "Ward", "District", "City", "0123456789"
        );
        when(cinemaRepository.existsByName(request.getName())).thenReturn(true);

        // When & Then
        assertThrows(ResourceAlreadyExistsException.class, () -> cinemaService.createCinema(request, Collections.emptyList()));
        verify(cinemaRepository, never()).save(any());
    }
}
