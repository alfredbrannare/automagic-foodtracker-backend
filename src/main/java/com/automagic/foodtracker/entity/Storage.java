package com.automagic.foodtracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "storage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double totalWeight;

    private double consumedWeight;

    @Column(nullable = false)
    private double weightPerMeal;

    @Embedded
    @Column(nullable = false)
    private Nutrition nutritionPer100g;

    private double lowStockThreshold;

    @Column(nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    //Helper Methods
    public double getRemainingWeight() {
        return totalWeight - consumedWeight;
    }

    public double getMealsLeft() {
        return getRemainingWeight() / weightPerMeal;
    }

    public boolean isLowStock() {
        return getRemainingWeight() < lowStockThreshold;
    }

    public double getProgressPercentage() {
        return (consumedWeight / totalWeight) * 100;
    }

}