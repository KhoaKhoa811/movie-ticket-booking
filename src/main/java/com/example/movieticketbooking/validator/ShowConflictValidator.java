package com.example.movieticketbooking.validator;

import com.example.movieticketbooking.entity.ShowEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ConflictException;
import com.example.movieticketbooking.utils.ShowTimeUtils;

import java.time.LocalDate;
import java.util.List;

public class ShowConflictValidator {

    public static void validateInternalShowConflicts(List<ShowEntity> shows) {
        for (int i = 0; i < shows.size() - 1; i++) {
            for (int j = i + 1; j < shows.size(); j++) {
                ShowEntity show1 = shows.get(i);
                ShowEntity show2 = shows.get(j);

                if (ShowTimeUtils.isTimeConflict(
                        show1.getStartTime(), show1.getEndTime(),
                        show2.getStartTime(), show2.getEndTime())) {
                    throw new ConflictException(Code.SHOWS_CONFLICT_WITHIN_REQUEST);
                }
            }
        }
    }

    public static void validateWithExistingShows(List<ShowEntity> newShows, List<ShowEntity> existingShows) {
        for (ShowEntity newShow : newShows) {
            for (ShowEntity existing : existingShows) {
                if (ShowTimeUtils.isTimeConflict(
                        newShow.getStartTime(), newShow.getEndTime(),
                        existing.getStartTime(), existing.getEndTime())) {
                    throw new ConflictException(Code.SHOWS_CONFLICT_WITH_EXISTING_SHOWS);
                }
            }
        }
    }

    public static void validateWithMovieStartAndEndDate(LocalDate showDate, LocalDate startDate, LocalDate endDate) {
        if (showDate.isBefore(startDate) || showDate.isAfter(endDate)) {
            throw new ConflictException(Code.SHOWS_CONFLICT_WITHIN_REQUEST);
        }
    }
}
