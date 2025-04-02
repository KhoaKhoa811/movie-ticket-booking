package com.example.movieticketbooking.utils;

import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.FileProcessingException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Component
public class FileUtils {
    public static File convert(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new ResourceNotFoundException(Code.MULTIPART_FILE_NOT_FOUND);
        }

        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            fos.flush();
        } catch (IOException e) {
            throw new FileProcessingException(Code.FAIL_TO_CONVERT_FILE);
        }
        return file;
    }


    public static void deleteTempFile(File file) {
        try {
            if (!Files.deleteIfExists(file.toPath())) {
                throw new FileProcessingException(Code.FAIL_TO_DELETE_FILE);
            }
        } catch (IOException e) {
            throw new FileProcessingException(Code.FAIL_TO_DELETE_FILE);
        }
    }
}
