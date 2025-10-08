package com.automagic.foodtracker.entity;

import java.time.Instant;

public record Storage(
        String id,
        String userId,
        String name,
        double totalWeight,
        double consumedWeight,
        double weightPerMeal,
        Nutrition nutritionPer100g,
        double lowStockThreshold,
        Instant createdAt,
        Instant updatedAt
) {}