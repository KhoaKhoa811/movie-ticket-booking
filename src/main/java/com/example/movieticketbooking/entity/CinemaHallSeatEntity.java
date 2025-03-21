package com.example.movieticketbooking.entity;

import com.example.movieticketbooking.enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cinema_hall_seat")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHallEntity cinemaHall;
}
