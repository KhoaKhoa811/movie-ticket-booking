package com.example.movieticketbooking.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.movieticketbooking.service.impl.CloudinaryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CloudinaryServiceTest {
    @InjectMocks
    private CloudinaryServiceImpl cloudinaryService;
    @Mock
    private Cloudinary cloudinary;
    @Mock
    private Uploader uploader;
    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        Mockito.when(cloudinary.uploader()).thenReturn(uploader);
    }

    @Test
    void testUploadImage() throws Exception {
        // Giả lập MultipartFile
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("test.jpg");
        Mockito.when(multipartFile.getBytes()).thenReturn("fake-image-content".getBytes());
        // Giả lập Cloudinary upload
        Map<String, Object> uploadResult = Map.of("url", "https://cloudinary.com/test.jpg");
        Mockito.when(uploader.upload(Mockito.any(File.class), Mockito.anyMap())).thenReturn(uploadResult);
        // Thực hiện test
        Map<String, Object> result = cloudinaryService.uploadImage(multipartFile, "test-folder");
        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals("https://cloudinary.com/test.jpg", result.get("url"));
        // Kiểm tra xem phương thức upload đã được gọi
        Mockito.verify(uploader).upload(Mockito.any(File.class), Mockito.anyMap());
    }

    @Test
    void testDeleteImage() throws Exception {
        // Giả lập Cloudinary delete
        Map<String, Object> deleteResult = Map.of("result", "ok");
        Mockito.when(uploader.destroy(Mockito.anyString(), Mockito.anyMap())).thenReturn(deleteResult);
        // Thực hiện test
        Map<String, Object> result = cloudinaryService.deleteImage("test-image-id", "test-folder");
        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals("ok", result.get("result"));
        // Kiểm tra xem phương thức delete đã được gọi
        Mockito.verify(uploader).destroy(Mockito.anyString(), Mockito.anyMap());
    }
}
