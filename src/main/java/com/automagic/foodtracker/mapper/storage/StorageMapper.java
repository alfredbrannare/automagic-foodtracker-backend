package com.automagic.foodtracker.mapper.storage;

import com.automagic.foodtracker.dto.request.storage.CreateStorageRequest;
import com.automagic.foodtracker.dto.request.storage.UpdateStorageRequest;
import com.automagic.foodtracker.dto.response.storage.StorageResponse;
import com.automagic.foodtracker.entity.Storage;
import com.automagic.foodtracker.exception.storage.BadStorageRequestException;

import java.time.Instant;

public class StorageMapper {

    public static Storage toEntity(CreateStorageRequest request) {
        Storage storage = new Storage();
        storage.setName(request.getName());
        storage.setNutritionPer100g(request.getNutritionPer100g());
        storage.setTotalWeight(request.getTotalWeight());
        storage.setWeightPerMeal(request.getWeightPerMeal());
        storage.setLowStockThreshold(request.getLowStockThreshold());

        if (request.getCreatedAt() != null) {
            storage.setCreatedAt(request.getCreatedAt());
        } else {
            storage.setCreatedAt(Instant.now());
        }

        return storage;
    }

    public static Storage toEntity(String storageId, UpdateStorageRequest request) {
        if (request.getLowStockThreshold() > request.getTotalWeight()) {
            throw new BadStorageRequestException("Low stock threshold cannot be greater than total weight");
        }

        if (request.getWeightPerMeal() > request.getTotalWeight()) {
            throw new BadStorageRequestException("Weight per meal cannot be greater than total weight");
        }

        Storage storage = new Storage();
        storage.setId(storageId);
        storage.setName(request.getName());
        storage.setNutritionPer100g(request.getNutritionPer100g());
        storage.setTotalWeight(request.getTotalWeight());
        storage.setWeightPerMeal(request.getWeightPerMeal());
        storage.setLowStockThreshold(request.getLowStockThreshold());
        storage.setCreatedAt(request.getCreatedAt());

        return storage;
    }

    public static StorageResponse toResponse(Storage storage) {
        return StorageResponse.builder()
                .id(storage.getId())
                .name(storage.getName())
                .totalWeight(storage.getTotalWeight())
                .consumedWeight(storage.getConsumedWeight())
                .weightPerMeal(storage.getWeightPerMeal())
                .lowStockThreshold(storage.getLowStockThreshold())
                .createdAt(storage.getCreatedAt())

                .remainingWeight(storage.getRemainingWeight())
                .mealsLeft(storage.getMealsLeft())
                .progressPercentage(storage.getProgressPercentage())

                .lowStock(storage.isLowStock())

                .build();
    }
}
