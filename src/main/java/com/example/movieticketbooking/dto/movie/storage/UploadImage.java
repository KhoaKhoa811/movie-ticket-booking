package com.example.movieticketbooking.dto.movie.storage;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadImage {
    private String imagePath;
    private String imageId;
}
