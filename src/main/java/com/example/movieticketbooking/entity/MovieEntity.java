package com.example.movieticketbooking.entity;

import com.example.movieticketbooking.enums.MovieType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movie")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    private String imagePath;
    private String imageId;
    private String description;
    private String director;
    @Column(name = "duration_in_mins")
    private Integer durationInMinutes;
    private LocalDate releaseDate;
    private String language;
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    @Column(name = "style")
    @Enumerated(EnumType.STRING)
    private MovieType movieType;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<GenreEntity> genres;
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ShowEntity> shows;
}
