package com.automagic.foodtracker.dto.request.storage;

import com.automagic.foodtracker.entity.Nutrition;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @AssertTrue(message = "Low stock threshold cannot be greater than total weight")
    private boolean isThresholdValid() {
        return lowStockThreshold <= totalWeight;
    }

    @AssertTrue(message = "Weight per meal cannot be greater than total weight")
    private boolean isMealWeightValid() {
        return weightPerMeal <= totalWeight;
    }
}
