package com.automagic.foodtracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

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
    private String userId;
    private String name;
    private double totalWeight;
    private double consumedWeight;
    private double weightPerMeal;
    private Nutrition nutritionPer100g;
    private double lowStockThreshold;
    private Instant createdAt;
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