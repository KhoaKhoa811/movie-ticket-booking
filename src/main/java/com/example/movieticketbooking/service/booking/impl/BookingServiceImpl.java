package com.example.movieticketbooking.service.booking.impl;

import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.booking.request.BookingInfoRequest;
import com.example.movieticketbooking.dto.booking.request.BookingRequest;
import com.example.movieticketbooking.dto.booking.request.ConfirmPaymentRequest;
import com.example.movieticketbooking.dto.booking.response.BookingInfoResponse;
import com.example.movieticketbooking.dto.booking.response.BookingResponse;
import com.example.movieticketbooking.entity.AccountEntity;
import com.example.movieticketbooking.entity.BookingEntity;
import com.example.movieticketbooking.entity.TicketEntity;
import com.example.movieticketbooking.enums.BookingStatus;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.IllegalStateException;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.mapper.BookingMapper;
import com.example.movieticketbooking.repository.AccountRepository;
import com.example.movieticketbooking.repository.BookingRepository;
import com.example.movieticketbooking.repository.TicketRepository;
import com.example.movieticketbooking.service.booking.BookingInfoService;
import com.example.movieticketbooking.service.booking.BookingService;
import com.example.movieticketbooking.service.TicketService;
import com.example.movieticketbooking.service.email.EmailSenderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final RedisTemplate<String, Integer> redisTemplate;
    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;

    private static final String RESERVE_KEY_FORMAT = "reserved_ticket:%d"; // ticketId
    private static final long RESERVE_TTL_SECONDS = 300L;  // 5 minutes TTL
    private final AccountRepository accountRepository;
    private final BookingMapper bookingMapper;
    private final TicketService ticketService;
    private final EmailSenderService emailSenderService;
    private final BookingInfoService bookingInfoService;

    // Confirm booking if reserved
    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        Integer accountId = request.getAccountId();
        List<Integer> ticketIds = request.getTicketIds();

        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(Code.ACCOUNT_NOT_FOUND));

        // lock tickets for 5 minutes
        this.reserveTickets(request);

        List<TicketEntity> tickets = ticketRepository.findAllById(ticketIds);
        if (tickets.size() != ticketIds.size()) {
            throw new ResourceNotFoundException(Code.TICKET_NOT_FOUND);
        }

        for (TicketEntity ticket : tickets) {
            String key = String.format(RESERVE_KEY_FORMAT, ticket.getId());
            Integer lockedUser = redisTemplate.opsForValue().get(key);

            if (!accountId.equals(lockedUser)) {
                throw new IllegalStateException(Code.WRONG_ACCOUNT_BOOKING);
            }

            if (Boolean.TRUE.equals(ticket.getIsBooked())) {
                throw new IllegalStateException(Code.TICKET_ALREADY_BOOKED);
            }
        }

        // try catch exception when optimistic locking happens
        try {
            // if all tickets are confirmed, create booking and update tickets
            BookingEntity booking = BookingEntity.builder()
                    .account(accountEntity)
                    .numberOfSeats(tickets.size())
                    .totalPrice(tickets.stream().mapToDouble(TicketEntity::getPrice).sum())
                    .createdAt(LocalDateTime.now())
                    .status(BookingStatus.PENDING)
                    .tickets(tickets)
                    .build();
            bookingRepository.save(booking);

            for (TicketEntity ticket : tickets) {
                ticket.setIsBooked(false);
                ticket.setIssuedAt(LocalDateTime.now());
            }

            ticketRepository.saveAll(tickets);
            return bookingMapper.toResponse(booking);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new IllegalStateException(Code.TICKET_CONFLICT);
        }
    }

    public void reserveTickets(BookingRequest request) {
        for (Integer ticketId : request.getTicketIds()) {
            String key = String.format(RESERVE_KEY_FORMAT, ticketId);
            Boolean success = redisTemplate.opsForValue()
                    .setIfAbsent(key, request.getAccountId(), Duration.ofSeconds(RESERVE_TTL_SECONDS));

            if (Boolean.FALSE.equals(success)) {
                throw new IllegalStateException(Code.TICKET_ALREADY_LOCKED);
            }
        }
    }

    @Override
    @Transactional
    public BookingResponse confirmPayment(ConfirmPaymentRequest request) {
        BookingEntity booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException(Code.BOOKING_NOT_FOUND));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException(Code.BOOKING_INVALID_STATUS);
        }

        List<TicketEntity> tickets = booking.getTickets();

        try {
            for (TicketEntity ticket : tickets) {
                String redisKey = String.format(RESERVE_KEY_FORMAT, ticket.getId());

                if (Boolean.TRUE.equals(ticket.getIsBooked())) {
                    throw new IllegalStateException(Code.TICKET_ALREADY_BOOKED);
                }

                ticket.setIsBooked(true);
                ticket.setIssuedAt(LocalDateTime.now());

                redisTemplate.delete(redisKey); // xoá khỏi Redis
            }

            booking.setStatus(BookingStatus.CONFIRMED);
            BookingEntity savedBooking = bookingRepository.save(booking);

            ticketRepository.saveAll(tickets);

            BookingInfoRequest bookingInfoRequest = bookingInfoService.getBookingInfo(savedBooking);
            emailSenderService.sendBookingEmail(bookingInfoRequest);

            return bookingMapper.toResponse(booking);
        } catch (ObjectOptimisticLockingFailureException e) {
            this.cancelExpiredBooking(request.getBookingId());
            throw new IllegalStateException(Code.TICKET_CONFLICT);
        }
    }

    public void cancelExpiredBooking(Integer bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(Code.BOOKING_NOT_FOUND));

        if (booking.getStatus().equals(BookingStatus.PENDING)) {
            booking.setStatus(BookingStatus.CANCELED);
            bookingRepository.save(booking);

            ticketService.releaseTicket(bookingId);
        }

    }

    @Override
    public PagedResponse<BookingInfoResponse> getAllBooking(Pageable pageable) {
        // get all booking info
        Page<BookingEntity> bookingPage = bookingRepository.findAll(pageable);
        // mapping to paged response
        List<BookingInfoResponse> content = bookingPage.getContent()
                .stream()
                .map(bookingInfoService::getBookingInfoResponse)
                .toList();
        // mapping to paged response
        return PagedResponse.<BookingInfoResponse>builder()
                .content(content)
                .page(bookingPage.getNumber())
                .size(bookingPage.getSize())
                .totalElements(bookingPage.getTotalElements())
                .totalPages(bookingPage.getTotalPages())
                .last(bookingPage.isLast())
                .build();
    }

}
