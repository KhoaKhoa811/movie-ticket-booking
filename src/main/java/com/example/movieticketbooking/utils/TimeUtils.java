package com.example.movieticketbooking.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class TimeUtils {

    public LocalTime calculateEndTime(LocalTime startTime, Integer durationInMinutes) {
        return startTime.plusMinutes(durationInMinutes);
    }
}
