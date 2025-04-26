package com.example.movieticketbooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double price;
    private Boolean isBooked;
    private String ticketCode;
    private LocalDateTime issuedAt;
    @Version
    private Long version;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "show_id")
    private ShowEntity show;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "seat_id")
    private CinemaHallSeatEntity seat;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "booking_ticket",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id")
    )
    private List<BookingEntity> booking;

}
