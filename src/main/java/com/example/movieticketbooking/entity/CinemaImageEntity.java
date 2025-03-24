package com.example.movieticketbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cinema_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "img_url", nullable = false)
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "cinema_id")
    private CinemaEntity cinema;
}
