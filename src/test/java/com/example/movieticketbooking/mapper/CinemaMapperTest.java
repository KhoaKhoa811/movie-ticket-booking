package com.example.movieticketbooking.mapper;

import com.example.movieticketbooking.dto.cinema.response.CinemaResponse;
import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaImageEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CinemaMapperTest {
    @Autowired
    private CinemaMapper cinemaMapper;

    @Test
    void toResponse_ShouldMapEntityToResponseCorrectly() {
        // Given
        CinemaEntity cinemaEntity = new CinemaEntity();
        cinemaEntity.setId(1);
        cinemaEntity.setName("Galaxy Cinema");
        cinemaEntity.setAddress("123 Main St");
        cinemaEntity.setStreet("Street A");
        cinemaEntity.setWard("Ward 1");
        cinemaEntity.setDistrict("District 2");
        cinemaEntity.setCity("Ho Chi Minh");
        cinemaEntity.setPhoneNumber("0123456789");

        // Giả lập danh sách ảnh
        List<CinemaImageEntity> images = List.of(
                new CinemaImageEntity(1, "https://example.com/image1.jpg", "img1", cinemaEntity),
                new CinemaImageEntity(2, "https://example.com/image2.jpg", "img2", cinemaEntity)
        );
        cinemaEntity.setCinemaImages(images);

        // When
        CinemaResponse response = cinemaMapper.toResponse(cinemaEntity);

        // Then
        assertNotNull(response);
        assertEquals(cinemaEntity.getId(), response.getId());
        assertEquals(cinemaEntity.getName(), response.getName());
        assertEquals(cinemaEntity.getAddress(), response.getAddress());
        assertEquals(cinemaEntity.getStreet(), response.getStreet());
        assertEquals(cinemaEntity.getWard(), response.getWard());
        assertEquals(cinemaEntity.getDistrict(), response.getDistrict());
        assertEquals(cinemaEntity.getCity(), response.getCity());
        assertEquals(cinemaEntity.getPhoneNumber(), response.getPhoneNumber());

        // Kiểm tra mapping của danh sách ảnh
        assertNotNull(response.getImages());
        assertEquals(2, response.getImages().size());
        assertEquals("https://example.com/image1.jpg", response.getImages().get(0).getImageUrl());
        assertEquals("img1", response.getImages().get(0).getImageId());
        assertEquals("https://example.com/image2.jpg", response.getImages().get(1).getImageUrl());
        assertEquals("img2", response.getImages().get(1).getImageId());
    }
}

