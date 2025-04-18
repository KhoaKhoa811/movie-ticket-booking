package com.example.movieticketbooking.utils;

import com.example.movieticketbooking.entity.ShowEntity;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class ShowTimeUtils {

    public static LocalTime calculateEndTime(LocalTime startTime, Integer durationInMinutes) {
        return startTime.plusMinutes(durationInMinutes);
    }

    public static void assignEndTimes(List<ShowEntity> shows, Integer durationInMinutes) {
        shows.forEach(show -> {
            LocalTime endTime = calculateEndTime(show.getStartTime(), durationInMinutes);
            show.setEndTime(endTime);
        });
    }

    public static boolean isTimeConflict(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }

}
