package com.example.movieticketbooking.mapper.impl;

import com.example.movieticketbooking.dto.show.request.ShowCreateRequest;
import com.example.movieticketbooking.dto.show.request.SingleShowUpdateRequest;
import com.example.movieticketbooking.dto.show.response.ShowBasicResponse;
import com.example.movieticketbooking.dto.show.response.ShowResponse;
import com.example.movieticketbooking.entity.CinemaHallEntity;
import com.example.movieticketbooking.entity.MovieEntity;
import com.example.movieticketbooking.entity.ShowEntity;
import com.example.movieticketbooking.mapper.ShowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShowMapperImpl implements ShowMapper {

    @Override
    public List<ShowEntity> toShowEntities(ShowCreateRequest request, MovieEntity movie, CinemaHallEntity hall) {
        return request.getStartTimeList().stream()
                .map(startTime -> ShowEntity.builder()
                        .showDate(request.getShowDate())
                        .startTime(startTime)
                        .type(request.getType())
                        .isActive(request.getIsActive())
                        .movie(movie)
                        .cinemaHall(hall)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ShowResponse toShowResponse(ShowEntity showEntity) {
        return ShowResponse.builder()
                .id(showEntity.getId())
                .showDate(showEntity.getShowDate())
                .startTime(showEntity.getStartTime())
                .endTime(showEntity.getEndTime())
                .type(showEntity.getType())
                .isActive(showEntity.getIsActive())
                .movieId(showEntity.getMovie().getId())
                .movieTitle(showEntity.getMovie().getTitle())
                .cinemaHallId(showEntity.getCinemaHall().getId())
                .cinemaHallName(showEntity.getCinemaHall().getName())
                .build();
    }

    @Override
    public List<ShowResponse> toShowResponseList(List<ShowEntity> showEntities) {
        return showEntities.stream()
                .map(this::toShowResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ShowBasicResponse toShowBasicResponse(ShowEntity showEntity) {
        return ShowBasicResponse.builder()
                .id(showEntity.getId())
                .showDate(showEntity.getShowDate())
                .startTime(showEntity.getStartTime())
                .endTime(showEntity.getEndTime())
                .isActive(showEntity.getIsActive())
                .type(showEntity.getType())
                .build();
    }

    @Override
    public List<ShowBasicResponse> toShowBasicResponseList(List<ShowEntity> showEntities) {
        return showEntities.stream()
                .map(this::toShowBasicResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void toShowEntity(ShowEntity showEntity, SingleShowUpdateRequest singleShowUpdateRequest) {
        if (singleShowUpdateRequest.getShowDate() != null) {
            showEntity.setShowDate(singleShowUpdateRequest.getShowDate());
        }
        if (singleShowUpdateRequest.getStartTime() != null) {
            showEntity.setStartTime(singleShowUpdateRequest.getStartTime());
        }
        if (singleShowUpdateRequest.getType() != null) {
            showEntity.setType(singleShowUpdateRequest.getType());
        }
        if (singleShowUpdateRequest.getIsActive() != null) {
            showEntity.setIsActive(singleShowUpdateRequest.getIsActive());
        }
    }
}
