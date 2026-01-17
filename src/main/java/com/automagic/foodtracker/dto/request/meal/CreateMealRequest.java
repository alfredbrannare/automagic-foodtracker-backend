package com.automagic.foodtracker.dto.request.meal;

import com.automagic.foodtracker.entity.Nutrition;
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
public class CreateMealRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private double weight;

    @NotNull(message = "Nutrition is required")
    private Nutrition nutrition;

    private Instant consumedAt;
    private String storageId;
}
