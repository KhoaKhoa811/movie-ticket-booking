package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.genre.request.GenreCreateRequest;
import com.example.movieticketbooking.dto.genre.response.GenreResponse;
import com.example.movieticketbooking.entity.GenreEntity;
import com.example.movieticketbooking.enums.ErrorCode;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.mapper.GenreMapper;
import com.example.movieticketbooking.repository.GenreRepository;
import com.example.movieticketbooking.service.GenreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    @Transactional
    public GenreResponse createGenre(GenreCreateRequest request) {
        if (genreRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException(ErrorCode.GENRE_ALREADY_EXIST);
        }
        GenreEntity genreEntity = genreMapper.toEntity(request);
        GenreEntity savedEntity = genreRepository.save(genreEntity);
        return genreMapper.toResponse(savedEntity);
    }

    @Override
    public boolean existsGenre(String genreName) {
        return genreRepository.existsByName(genreName);
    }
}
