package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.movie.request.MovieCreateRequest;
import com.example.movieticketbooking.dto.movie.request.MovieUpdateRequest;
import com.example.movieticketbooking.dto.movie.response.MovieResponse;
import com.example.movieticketbooking.dto.movie.storage.UploadImage;
import com.example.movieticketbooking.entity.GenreEntity;
import com.example.movieticketbooking.entity.MovieEntity;
import com.example.movieticketbooking.enums.CloudinaryFolderName;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.MovieMapper;
import com.example.movieticketbooking.repository.GenreRepository;
import com.example.movieticketbooking.repository.MovieRepository;
import com.example.movieticketbooking.service.CloudinaryService;
import com.example.movieticketbooking.service.MovieImageService;
import com.example.movieticketbooking.service.MovieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final MovieImageService movieImageService;
    private final CloudinaryService cloudinaryService;
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public MovieResponse createMovie(MovieCreateRequest movieCreateRequest, MultipartFile movieImage) {
        if (movieRepository.existsByTitle(movieCreateRequest.getTitle())) {
            throw new ResourceAlreadyExistsException(Code.MOVIE_ALREADY_EXIST);
        }
        // map create request to entity
        MovieEntity movieEntity = movieMapper.toEntity(movieCreateRequest);
        // update image
        UploadImage savedImage = movieImageService.uploadMovieImage(movieCreateRequest.getTitle(), movieImage);
        movieEntity.setImagePath(savedImage.getImagePath());
        movieEntity.setImageId(savedImage.getImageId());
        // adding genres
        List<GenreEntity> genres = genreRepository.findAllById(movieCreateRequest.getGenreIds());
        movieEntity.setGenres(genres);
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

    @Override
    @Transactional
    public MovieResponse updateMovieById(Integer id, MovieUpdateRequest movieUpdateRequest) {
        // find the movie needed to update
        MovieEntity movieEntity = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.MOVIE_NOT_FOUND));
        // update new information
        movieMapper.updateMovieByUpdateRequest(movieEntity, movieUpdateRequest);
        // update
        if (movieUpdateRequest.getGenreIds() != null) {
            List<GenreEntity> genres = genreRepository.findAllById(movieUpdateRequest.getGenreIds());
            movieEntity.setGenres(genres);
        }
        MovieEntity savedMovie = movieRepository.save(movieEntity);
        return movieMapper.toResponse(savedMovie);
    }

    @Override
    public MovieResponse updateMovieImage(Integer movieId, MultipartFile movieImage) {
        // find movie needed to update image
        MovieEntity movieEntity = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(Code.MOVIE_NOT_FOUND));
        // delete old image
        cloudinaryService.deleteImage(movieEntity.getImageId());
        // update new image
        UploadImage updatedImage = movieImageService.uploadMovieImage(movieEntity.getTitle(), movieImage);
        // update new image information
        movieEntity.setImageId(updatedImage.getImageId());
        movieEntity.setImagePath(updatedImage.getImagePath());
        // save image
        MovieEntity savedMovie = movieRepository.save(movieEntity);
        return movieMapper.toResponse(savedMovie);
    }

    @Override
    public PagedResponse<MovieResponse> getAllMovie(Pageable pageable) {
        // find all by page
        Page<MovieEntity> moviePage = movieRepository.findAll(pageable);
        // get movies list
        List<MovieResponse> content = moviePage.getContent()
                .stream()
                .map(movieMapper::toResponse)
                .toList();
        // mapping to paged response
        return PagedResponse.<MovieResponse>builder()
                .content(content)
                .page(moviePage.getNumber())
                .size(moviePage.getSize())
                .totalElements(moviePage.getTotalElements())
                .totalPages(moviePage.getTotalPages())
                .last(moviePage.isLast())
                .build();
    }

    @Override
    public PagedResponse<MovieResponse> getAllAvailableMovieForDate(LocalDate date, Pageable pageable) {
        Page<MovieEntity> moviePage = movieRepository.findActiveAvailableMoviesByDate(date, true, pageable);
        // get movies list
        List<MovieResponse> content = moviePage.getContent()
                .stream()
                .map(movieMapper::toResponse)
                .toList();
        // mapping to paged response
        return PagedResponse.<MovieResponse>builder()
                .content(content)
                .page(moviePage.getNumber())
                .size(moviePage.getSize())
                .totalElements(moviePage.getTotalElements())
                .totalPages(moviePage.getTotalPages())
                .last(moviePage.isLast())
                .build();
    }

    @Override
    public PagedResponse<MovieResponse> getAllUpcomingMovieForDate(LocalDate date, Pageable pageable) {
        Page<MovieEntity> moviePage = movieRepository.findActiveUpcomingMoviesByDate(date, true, pageable);
        // get movies list
        List<MovieResponse> content = moviePage.getContent()
                .stream()
                .map(movieMapper::toResponse)
                .toList();
        // mapping to paged response
        return PagedResponse.<MovieResponse>builder()
                .content(content)
                .page(moviePage.getNumber())
                .size(moviePage.getSize())
                .totalElements(moviePage.getTotalElements())
                .totalPages(moviePage.getTotalPages())
                .last(moviePage.isLast())
                .build();
    }


}
