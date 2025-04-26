package com.example.movieticketbooking.entity;

import com.example.movieticketbooking.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer numberOfSeats;
    private Double totalPrice;
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "account_id")
    private AccountEntity account;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "booking_ticket",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id")
    )
    private List<TicketEntity> tickets;
}
