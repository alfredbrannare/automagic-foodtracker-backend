package com.automagic.foodtracker.dto.request.storage;

import com.automagic.foodtracker.entity.Nutrition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class CreateStorageRequest {
    @NotBlank
    String name;

    @NotNull(message = "Nutrition is required")
    Nutrition nutritionPer100g;

    @NotNull
    @Positive(message = "Weight must be positive")
    double totalWeight;

    @NotNull
    @Positive(message = "Weight must be positive")
    double weightPerMeal;

    @NotNull
    @Positive(message = "Weight must be positive")
    double lowStockThreshold;

    private Instant createdAt;
}
