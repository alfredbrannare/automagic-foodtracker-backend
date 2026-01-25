package com.automagic.foodtracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "cooking_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CookingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double totalWeight;

    @Column(nullable = false)
    private double weightPerMeal;

    @Embedded
    @Column(nullable = false)
    private Nutrition nutritionPer100g;

    private double lowStockThreshold;

    @Column(nullable = false)
    private Instant createdAt;
}