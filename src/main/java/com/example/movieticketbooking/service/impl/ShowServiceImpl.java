package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.show.request.MovieAndCinemaRequest;
import com.example.movieticketbooking.dto.show.request.ShowCreateRequest;
import com.example.movieticketbooking.dto.show.response.ShowBasicResponse;
import com.example.movieticketbooking.dto.show.response.ShowResponse;
import com.example.movieticketbooking.entity.CinemaHallEntity;
import com.example.movieticketbooking.entity.MovieEntity;
import com.example.movieticketbooking.entity.ShowEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.ShowMapper;
import com.example.movieticketbooking.repository.CinemaHallRepository;
import com.example.movieticketbooking.repository.CinemaRepository;
import com.example.movieticketbooking.repository.MovieRepository;
import com.example.movieticketbooking.repository.ShowRepository;
import com.example.movieticketbooking.service.ShowService;
import com.example.movieticketbooking.utils.ShowTimeUtils;
import com.example.movieticketbooking.validator.ShowConflictValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final CinemaHallRepository cinemaHallRepository;
    private final ShowMapper showMapper;
    private final CinemaRepository cinemaRepository;
    private final ShowService showService;

    @Override
    @Transactional
    public List<ShowResponse> createShows(ShowCreateRequest request) {
        // get movie
        MovieEntity movieEntity = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException(Code.MOVIE_NOT_FOUND));
        // get cinema hall
        CinemaHallEntity cinemaHallEntity = cinemaHallRepository.findById(request.getCinemaHallId())
                .orElseThrow(() -> new ResourceNotFoundException(Code.CINEMA_HALL_NOT_FOUND));
        // check: does show date conflict with start date and end date of the movie
        ShowConflictValidator.validateWithMovieStartAndEndDate(request.getShowDate(), movieEntity.getStartDate(), movieEntity.getEndDate());
        // convert show create request to show entities
        List<ShowEntity> showEntities = showMapper.toShowEntities(request, movieEntity, cinemaHallEntity);
        // calculate end time by start time and duration in minutes
        ShowTimeUtils.assignEndTimes(showEntities, movieEntity.getDurationInMinutes());
        // check: does show request conflict itself
        ShowConflictValidator.validateInternalShowConflicts(showEntities);
        // check: does show request conflict with existing shows
        // get existing shows at the same cinema hall and date
        List<ShowEntity> existingShows = showRepository.findByCinemaHallIdAndShowDate(
                request.getCinemaHallId(), request.getShowDate()
        );
        // check conflict by start time and end time
        ShowConflictValidator.validateWithExistingShows(showEntities, existingShows);
        // save shows
        List<ShowEntity> savedShows = showRepository.saveAll(showEntities);
        return showMapper.toShowResponseList(savedShows);
    }

    @Override
    public List<ShowBasicResponse> getShowsByMovieIdAndCinemaId(MovieAndCinemaRequest movieAndCinemaRequest) {
        if (!movieRepository.existsById(movieAndCinemaRequest.getMovieId())) {
            throw new ResourceNotFoundException(Code.MOVIE_NOT_FOUND);
        }
        if (!cinemaRepository.existsById(movieAndCinemaRequest.getCinemaId())) {
            throw new ResourceNotFoundException(Code.CINEMA_NOT_FOUND);
        }
        // get show by movie id and cinema hall id
        List<ShowEntity> shows = showRepository.findByMovieIdAndCinemaHallIds(
                movieAndCinemaRequest.getMovieId(),
                movieAndCinemaRequest.getCinemaId()
        );
        // map to response
        return showMapper.toShowBasicResponseList(shows);
    }

    @Override
    public List<ShowBasicResponse> getShowsByMovieIdAndCinemaIdAndShowDate(MovieAndCinemaRequest movieAndCinemaRequest, LocalDate showDate) {
        if (!movieRepository.existsById(movieAndCinemaRequest.getMovieId())) {
            throw new ResourceNotFoundException(Code.MOVIE_NOT_FOUND);
        }
        if (!cinemaRepository.existsById(movieAndCinemaRequest.getCinemaId())) {
            throw new ResourceNotFoundException(Code.CINEMA_NOT_FOUND);
        }
        // get show by movie id and cinema hall id and show date
        List<ShowEntity> shows = showRepository.findByMovieIdAndCinemaHallIdsAndShowDate(
                movieAndCinemaRequest.getMovieId(),
                movieAndCinemaRequest.getCinemaId(),
                showDate
        );
        // map to response
        return showMapper.toShowBasicResponseList(shows);
    }

    @Override
    public void removeShowById(Integer id) {
        if (!showRepository.existsById(id)) {
            throw new ResourceNotFoundException(Code.SHOWS_NOT_FOUND);
        }
        showRepository.deleteById(id);
    }


}
