package com.automagic.foodtracker.dto.request.meal;

import com.automagic.foodtracker.entity.Nutrition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class CreateMealRequest {
    @NotBlank
    private String name;

    @Positive(message = "Weight must be positive")
    private double weight;

    @NotNull(message = "Nutrition is required")
    private Nutrition nutrition;

    private String storageId;
    private Instant consumedAt;
}
