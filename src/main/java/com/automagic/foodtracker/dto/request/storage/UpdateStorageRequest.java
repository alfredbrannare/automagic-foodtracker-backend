package com.automagic.foodtracker.dto.request.storage;

import com.automagic.foodtracker.entity.Nutrition;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class UpdateStorageRequest {
    @NotNull
    private String name;

    @NotNull
    private double totalWeight;

    @NotNull
    private Nutrition nutritionPer100g;

    @NotNull
    private double weightPerMeal;

    @NotNull
    private double lowStockThreshold;

    private Instant createdAt;
}
