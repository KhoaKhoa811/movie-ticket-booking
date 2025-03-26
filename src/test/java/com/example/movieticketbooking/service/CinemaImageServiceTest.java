package com.example.movieticketbooking.service;

import com.example.movieticketbooking.entity.CinemaEntity;
import com.example.movieticketbooking.entity.CinemaImageEntity;
import com.example.movieticketbooking.repository.CinemaImageRepository;
import com.example.movieticketbooking.service.impl.CinemaImageServiceImpl;
import com.example.movieticketbooking.service.impl.CloudinaryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CinemaImageServiceTest {
    @Mock
    private CloudinaryServiceImpl cloudinaryService;
    @Mock
    private CinemaImageRepository cinemaImageRepository;
    @InjectMocks
    private CinemaImageServiceImpl cinemaImageService;

    @Test
    void uploadAndSaveImages_ShouldUploadAndSaveCorrectly() {
        // Given
        CinemaEntity cinemaEntity = new CinemaEntity();
        MultipartFile mockFile = mock(MultipartFile.class);
        List<MultipartFile> images = Collections.singletonList(mockFile);

        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("url", "http://image.url");
        uploadResult.put("public_id", "image123");

        when(cloudinaryService.uploadImage(any(), any())).thenReturn(uploadResult);
        // Giả lập hành vi saveAll() để trả về danh sách ảnh đã lưu
        when(cinemaImageRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        List<CinemaImageEntity> result = cinemaImageService.uploadAndSaveImage(cinemaEntity, images);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("http://image.url", result.get(0).getImageUrl());
        verify(cinemaImageRepository).saveAll(any());
    }

    @Test
    void uploadAndSaveImages_WhenNoImages_ShouldReturnEmptyList() {
        // Given
        CinemaEntity cinemaEntity = new CinemaEntity();
        List<MultipartFile> images = Collections.emptyList();

        // When
        List<CinemaImageEntity> result = cinemaImageService.uploadAndSaveImage(cinemaEntity, images);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cinemaImageRepository, never()).saveAll(any());
    }

}
