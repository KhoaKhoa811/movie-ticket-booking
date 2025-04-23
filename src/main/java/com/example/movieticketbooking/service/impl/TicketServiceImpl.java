package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.entity.CinemaHallSeatEntity;
import com.example.movieticketbooking.entity.ShowEntity;
import com.example.movieticketbooking.entity.TicketEntity;
import com.example.movieticketbooking.repository.CinemaHallSeatRepository;
import com.example.movieticketbooking.repository.TicketRepository;
import com.example.movieticketbooking.service.TicketService;
import com.example.movieticketbooking.utils.TicketUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final CinemaHallSeatRepository cinemaHallSeatRepository;
    private final ConcurrentMap<Integer, Object> showLocks = new ConcurrentHashMap<>();

    @Override
    @Transactional
    @Async("taskExecutor")
    public void generateTicket(ShowEntity showEntity) {
        // Lock show entity to prevent concurrent access
        Object lock = showLocks.computeIfAbsent(showEntity.getId(), id -> new Object());
        // Check if show entity has already generated tickets
        synchronized (lock) {
            if (ticketRepository.existsByShowId((showEntity.getId())))
                return;
            // Generate tickets for show entity
            Integer CinemaHallId = showEntity.getCinemaHall().getId();
            List<CinemaHallSeatEntity> cinemaHallSeatEntities = cinemaHallSeatRepository.findByCinemaHallIdOrderById(CinemaHallId);
            List<TicketEntity> ticketEntities = cinemaHallSeatEntities.stream().map(
                    seatEntity -> TicketEntity.builder()
                            .price(TicketUtils.calculatePrice(seatEntity))
                            .isBooked(false)
                            .ticketCode(TicketUtils.generateTicketCode(showEntity, seatEntity))
                            .issuedAt(LocalDateTime.now())
                            .show(showEntity)
                            .seat(seatEntity)
                            .build()
            ).toList();
            // Save tickets to database
            ticketRepository.saveAll(ticketEntities);
            // Unlock show entity after generating tickets
            showLocks.remove(showEntity.getId());
        }
    }
}
