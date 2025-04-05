package com.example.movieticketbooking.entity;

import com.example.movieticketbooking.enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seat_template_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatTemplateDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Character seatRow;
    private Integer seatColumn;
    private SeatType type;
    private Boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "seat_template_id")
    private SeatTemplateEntity seatTemplate;
}
