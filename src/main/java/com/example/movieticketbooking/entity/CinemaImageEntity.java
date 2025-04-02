package com.example.movieticketbooking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cinema_images")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CinemaImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "img_url", nullable = false)
    private String imageUrl;
    @Column(name = "img_id")
    private String imageId;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "cinema_id")
    private CinemaEntity cinema;
}
