package com.automagic.foodtracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Objects;

@Data
@NoArgsConstructor
@Document(collection = "storage")
public class Storage {
    @Id
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