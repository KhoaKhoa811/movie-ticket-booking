package com.example.movieticketbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cinema_hall")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaHallEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(name = "total_seat", nullable = false)
    private Integer totalSeats;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "cinema_id")
    private CinemaEntity cinema;
    @OneToMany(mappedBy = "cinemaHall", cascade = CascadeType.ALL)
    private List<CinemaHallSeatEntity> cinemaHallSeats;
}
