package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.movie.request.MovieCreateRequest;
import com.example.movieticketbooking.dto.movie.response.MovieResponse;
import com.example.movieticketbooking.dto.movie.storage.UploadImage;
import com.example.movieticketbooking.entity.MovieEntity;
import com.example.movieticketbooking.enums.CloudinaryFolderName;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.MovieMapper;
import com.example.movieticketbooking.repository.MovieRepository;
import com.example.movieticketbooking.service.CloudinaryService;
import com.example.movieticketbooking.service.MovieImageService;
import com.example.movieticketbooking.service.MovieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final MovieImageService movieImageService;
    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public MovieResponse createMovie(MovieCreateRequest movieCreateRequest, MultipartFile movieImage) {
        if (movieRepository.existsByTitle(movieCreateRequest.getTitle())) {
            throw new ResourceAlreadyExistsException(Code.MOVIE_ALREADY_EXIST);
        }
        // map create request to entity
        MovieEntity movieEntity = movieMapper.toEntity(movieCreateRequest);
        // update image
        UploadImage savedImage = movieImageService.uploadMovieImage(movieCreateRequest, movieImage);
        movieEntity.setImagePath(savedImage.getImagePath());
        movieEntity.setImageId(savedImage.getImageId());
        // save movie
        MovieEntity savedMovie = movieRepository.save(movieEntity);
        return movieMapper.toResponse(savedMovie);
    }

    @Override
    public MovieResponse getMovieById(Integer id) {
        MovieEntity movieEntity = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.MOVIE_NOT_FOUND));
        return movieMapper.toResponse(movieEntity);
    }

    @Override
    @Transactional
    public void removeMovieById(Integer id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.MOVIE_NOT_FOUND);
        }
        String title = movieRepository.findTitleById(id);
        cloudinaryService.deleteFolder(CloudinaryFolderName.MOVIE.getCinemaFolder() + "/" + title);
        movieRepository.deleteById(id);
    }


}
