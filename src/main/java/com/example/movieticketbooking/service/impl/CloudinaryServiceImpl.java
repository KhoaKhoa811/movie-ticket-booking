package com.example.movieticketbooking.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
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
import java.util.List;
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
    public Map<String, Object> deleteResource(String id, String resourceType) {
        Map<String, Object> result;
        try{
            result = cloudinary.uploader().destroy(id,
                    ObjectUtils.asMap(
                            "resource_type", resourceType
                    ));
        } catch (IOException e) {
            throw new FileProcessingException(Code.FAIL_TO_DELETE_FILE);
        }
        return result;
    }

    @Override
    public Map<String, Object> deleteImage(String id) {
        return deleteResource(id, "image");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteFolder(String folderName) {
        try {
            // Lấy danh sách tất cả file trong thư mục
            ApiResponse apiResponse = cloudinary.api().resources(
                    ObjectUtils.asMap("type", "upload", "prefix", folderName + "/")
            );
            Map<String, Object> result = (Map<String, Object>) apiResponse; // Lấy kết quả an toàn

            // Ép kiểu và lấy danh sách public_id
            List<String> publicIds = ((List<Map<String, Object>>) result.get("resources"))
                    .stream()
                    .map(res -> (String) res.get("public_id"))
                    .toList();
            // Xóa từng file
            for (String publicId : publicIds) {
                deleteImage(publicId);
            }
            // Sau khi xóa hết file, mới xóa thư mục
            cloudinary.api().deleteFolder(folderName, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new FileProcessingException(Code.FAIL_TO_DELETE_FILE);
        }
    }


}
