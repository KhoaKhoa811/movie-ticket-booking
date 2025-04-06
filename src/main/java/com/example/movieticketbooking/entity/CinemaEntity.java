package com.example.movieticketbooking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.util.List;

@Entity
@Table(name = "cinema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String ward;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false)
    private String phoneNumber;
    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<CinemaHallEntity> cinemaHalls;
    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<CinemaImageEntity> cinemaImages;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "city_id")
    private CityEntity city;
}
