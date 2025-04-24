package com.example.movieticketbooking.listener;

import com.example.movieticketbooking.entity.BookingEntity;
import com.example.movieticketbooking.entity.TicketEntity;
import com.example.movieticketbooking.enums.BookingStatus;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.repository.BookingRepository;
import com.example.movieticketbooking.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketReleaseService {
    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public void releaseTicketByTicketId(Integer ticketId) {
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException(Code.TICKET_NOT_FOUND));

        BookingEntity booking = ticket.getBooking();
        if (booking != null && booking.getStatus() == BookingStatus.PENDING) {
            booking.setStatus(BookingStatus.CANCELED);
            bookingRepository.save(booking);

            ticket.setBooking(null);
            ticketRepository.save(ticket);

            log.info("Auto-canceled bookingId={} and released ticketId={}", booking.getId(), ticketId);
        }
    }
}
