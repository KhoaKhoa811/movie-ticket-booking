package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.genre.request.GenreCreateRequest;
import com.example.movieticketbooking.dto.genre.response.GenreResponse;
import com.example.movieticketbooking.entity.GenreEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceAlreadyExistsException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.GenreMapper;
import com.example.movieticketbooking.repository.GenreRepository;
import com.example.movieticketbooking.service.GenreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    @Transactional
    public GenreResponse createGenre(GenreCreateRequest request) {
        if (genreRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException(Code.GENRE_ALREADY_EXIST);
        }
        GenreEntity genreEntity = genreMapper.toEntity(request);
        GenreEntity savedEntity = genreRepository.save(genreEntity);
        return genreMapper.toResponse(savedEntity);
    }

    @Override
    @Transactional
    public void removeGenre(Integer id) {
        if (!genreRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.GENRE_NOT_FOUND);
        }
        genreRepository.deleteById(id);
    }

    @Override
    public List<GenreResponse> getAllGenre() {
        return genreRepository.findAllGenre().stream()
                .map(genreMapper::toResponse)
                .collect(Collectors.toList());
    }


}
