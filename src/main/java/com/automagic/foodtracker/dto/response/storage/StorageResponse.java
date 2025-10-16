package com.automagic.foodtracker.dto.response.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StorageResponse {
    private String id;
    private String name;
    private double totalWeight;
    private double consumedWeight;
    private double weightPerMeal;
    private double lowStockThreshold;
    private Instant createdAt;
    private Instant updatedAt;
}
