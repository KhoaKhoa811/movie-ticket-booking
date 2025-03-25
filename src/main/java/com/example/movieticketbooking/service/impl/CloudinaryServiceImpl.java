package com.example.movieticketbooking.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.FileProcessingException;
import com.example.movieticketbooking.service.CloudinaryService;
import com.example.movieticketbooking.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> uploadResource(MultipartFile multipartFile, String folderName, String resourceType) {
        File file = FileUtils.convert(multipartFile);
        Map<String, Object> result;
        try{
            result = cloudinary.uploader().upload(file,
                    ObjectUtils.asMap(
                            "resource_type", resourceType,
                            "folder", folderName
                    ));
        } catch(IOException e) {
            throw new FileProcessingException(Code.FAIL_TO_UPDATE_FILE);
        }
        FileUtils.deleteTempFile(file);
        return result;
    }

    @Override
    public Map<String, Object> uploadImage(MultipartFile multipartFile, String folderName) {
        return uploadResource(multipartFile, folderName, "image");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> deleteResource(String id, String folderName, String resourceType) {
        Map<String, Object> result;
        try{
            result = cloudinary.uploader().destroy(id,
                    ObjectUtils.asMap(
                            "resource_type", resourceType,
                            "folder", folderName
                    ));
        } catch (IOException e) {
            throw new FileProcessingException(Code.FAIL_TO_DELETE_FILE);
        }
        return result;
    }

    @Override
    public Map<String, Object> deleteImage(String id, String folderName) {
        return deleteResource(id, folderName, "image");
    }
}
