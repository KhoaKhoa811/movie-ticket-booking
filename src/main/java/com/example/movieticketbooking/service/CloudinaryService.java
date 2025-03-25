package com.example.movieticketbooking.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    Map<String, Object> uploadResource(MultipartFile multipartFile, String folderName, String resourceType);
    Map<String, Object> uploadImage(MultipartFile multipartFile, String folderName);
    Map<String, Object> deleteResource(String id, String folderName, String resourceType);
    Map<String, Object> deleteImage(String id, String folderName);
}
