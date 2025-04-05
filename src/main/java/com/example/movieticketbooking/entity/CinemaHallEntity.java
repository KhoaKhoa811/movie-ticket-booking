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
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "cinema_id")
    private CinemaEntity cinema;
    @OneToMany(mappedBy = "cinemaHall", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CinemaHallSeatEntity> cinemaHallSeats;
    @OneToMany(mappedBy = "cinemaHall", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ShowEntity> shows;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "seat_template_id")
    private SeatTemplateEntity seatTemplate;
}
