package com.example.movieticketbooking.entity.verification;

import com.example.movieticketbooking.entity.AccountEntity;
import com.example.movieticketbooking.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    @Column(name = "expire_time")
    private LocalDateTime expiryDate;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "account_id")
    private AccountEntity account;
}
