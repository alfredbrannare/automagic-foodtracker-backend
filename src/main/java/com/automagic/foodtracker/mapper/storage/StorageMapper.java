package com.automagic.foodtracker.mapper.storage;

import com.automagic.foodtracker.dto.response.storage.StorageResponse;
import com.automagic.foodtracker.entity.Storage;

public class StorageMapper {
    public static StorageResponse toResponse(Storage storage) {
        return StorageResponse.builder()
                .id(storage.getId())
                .name(storage.getName())
                .totalWeight(storage.getTotalWeight())
                .consumedWeight(storage.getConsumedWeight())
                .weightPerMeal(storage.getWeightPerMeal())
                .lowStockThreshold(storage.getLowStockThreshold())
                .createdAt(storage.getCreatedAt())
                .lowStock(storage.isLowStock())
                .build();
    }
}
