package com.example.movieticketbooking.entity;

import com.example.movieticketbooking.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "show_seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "show_id")
    private ShowEntity show;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "cinema_hall_seat_id")
    private CinemaHallSeatEntity cinemaHallSeat;
}
