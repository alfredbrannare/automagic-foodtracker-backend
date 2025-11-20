package com.automagic.foodtracker.dto.request.storage;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateStorageRequest {
    @NotNull
    private String name;
    private double totalWeight;
    private double weightPerMeal;
    private double lowStockThreshold;
}
