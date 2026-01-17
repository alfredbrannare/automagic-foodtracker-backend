package com.automagic.foodtracker.dto.request.meal;

import com.automagic.foodtracker.entity.Nutrition;
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
public class UpdateMealRequest {
    @NotNull
    private String name;

    @NotNull
    private double weight;

    @NotNull
    private Nutrition nutrition;

    @NotNull
    private Instant consumedAt;

    private String storageId;
}
