package com.automagic.foodtracker.dto.request.storage;

import com.automagic.foodtracker.entity.Nutrition;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStorageRequest {
    @NotBlank(message = "Name cannot be empty")
    String name;

    @NotNull(message = "Nutrition is required")
    Nutrition nutritionPer100g;

    @NotNull
    @Positive(message = "Total weight must be positive")
    double totalWeight;

    @NotNull
    @Positive(message = "Weight per meal must be positive")
    double weightPerMeal;

    @NotNull
    @Positive(message = "Low stock threshold must be positive")
    double lowStockThreshold;

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
