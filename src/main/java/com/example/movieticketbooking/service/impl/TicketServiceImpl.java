package com.example.movieticketbooking.service.impl;

import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.ticket.request.TicketUpdateRequest;
import com.example.movieticketbooking.dto.ticket.response.TicketResponse;
import com.example.movieticketbooking.dto.ticket.response.TicketWithSeatResponse;
import com.example.movieticketbooking.entity.CinemaHallSeatEntity;
import com.example.movieticketbooking.entity.ShowEntity;
import com.example.movieticketbooking.entity.TicketEntity;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.TicketMapper;
import com.example.movieticketbooking.repository.BookingRepository;
import com.example.movieticketbooking.repository.CinemaHallSeatRepository;
import com.example.movieticketbooking.repository.ShowRepository;
import com.example.movieticketbooking.repository.TicketRepository;
import com.example.movieticketbooking.service.TicketService;
import com.example.movieticketbooking.utils.TicketUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final TicketMapper ticketMapper;
    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;

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

    @Override
    public PagedResponse<TicketWithSeatResponse> getTicketsByShowId(Integer showId, Pageable pageable) {
        if (!showRepository.existsById(showId)) {
            throw new ResourceNotFoundException(Code.SHOWS_NOT_FOUND);
        }
        Page<TicketEntity> ticketEntities = ticketRepository.findByShowId(showId, pageable);
        // mapping to paged response
        List<TicketWithSeatResponse> content = ticketEntities.getContent()
                .stream()
                .map(ticketMapper::toWithSeatResponse)
                .toList();
        // mapping to paged response
        return PagedResponse.<TicketWithSeatResponse>builder()
                .content(content)
                .page(ticketEntities.getNumber())
                .size(ticketEntities.getSize())
                .totalElements(ticketEntities.getTotalElements())
                .totalPages(ticketEntities.getTotalPages())
                .last(ticketEntities.isLast())
                .build();
    }

    @Override
    public TicketResponse getTicketByShowIdAndSeatId(Integer showId, Integer seatId) {
        if (!showRepository.existsById(showId)) {
            throw new ResourceNotFoundException(Code.SHOWS_NOT_FOUND);
        }
        if (!cinemaHallSeatRepository.existsById(seatId)) {
            throw new ResourceNotFoundException(Code.SEAT_NOT_FOUND);
        }
        TicketEntity ticketEntity = ticketRepository.findByShowIdAndSeatId(showId, seatId)
                .orElseThrow(() -> new ResourceNotFoundException(Code.TICKET_NOT_FOUND));
        return ticketMapper.toResponse(ticketEntity);
    }

    @Override
    public TicketResponse getTicketById(Integer id) {
        TicketEntity ticketEntity = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.TICKET_NOT_FOUND));
        return ticketMapper.toResponse(ticketEntity);
    }

    @Override
    public TicketResponse updateTicket(Integer id, TicketUpdateRequest ticketUpdateRequest) {
        TicketEntity ticketEntity = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Code.TICKET_NOT_FOUND));
        ticketMapper.updateTicketFromRequest(ticketEntity, ticketUpdateRequest);
        TicketEntity savedTicketEntity = ticketRepository.save(ticketEntity);
        return ticketMapper.toResponse(savedTicketEntity);
    }

    @Override
    @Transactional
    public void releaseTicket(Integer bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException(Code.BOOKING_NOT_FOUND);
        }
        List<TicketEntity> ticketEntities = ticketRepository.findAllByBookingId(bookingId);
        if (ticketEntities.isEmpty()) {
            throw new ResourceNotFoundException(Code.TICKET_NOT_FOUND);
        }
        ticketEntities.forEach(ticketEntity -> {
            ticketEntity.setBooking(null);
            ticketEntity.setIsBooked(false);
        });
        ticketRepository.saveAll(ticketEntities);
    }
}
