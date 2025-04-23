package com.example.movieticketbooking.entity;

import com.example.movieticketbooking.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cinema_hall_seat")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaHallSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "seat_row", nullable = false)
    private Character seatRow;
    @Column(name = "seat_column", nullable = false)
    private Integer seatCol;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType type;
    private Boolean isActive;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHallEntity cinemaHall;
    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<TicketEntity> tickets;
}
