package com.example.movieticketbooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "seat_template")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatTemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Integer rowCount;
    private Integer columnCount;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "seatTemplate", cascade = CascadeType.ALL)
    private List<SeatTemplateDetailEntity> details;
    @OneToMany(mappedBy = "seatTemplate", cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private List<CinemaHallEntity> cinemaHalls;
}
