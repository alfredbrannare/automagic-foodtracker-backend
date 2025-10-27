package com.automagic.foodtracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    String userId;

    @Column(nullable = false, unique = true)
    String token;

    @Column(nullable = false)
    Instant expiresAt;
}
