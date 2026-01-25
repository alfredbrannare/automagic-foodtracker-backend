package com.automagic.foodtracker.mapper.cookinghistory;

import com.automagic.foodtracker.dto.response.cookinghistory.CookingHistoryResponse;
import com.automagic.foodtracker.entity.CookingHistory;
import com.automagic.foodtracker.entity.Storage;

import java.time.Instant;

public class CookingHistoryMapper {

    public static CookingHistory toEntity (Storage storage) {
        CookingHistory cookingHistory = new CookingHistory();
        cookingHistory.setName(storage.getName());
        cookingHistory.setNutritionPer100g(storage.getNutritionPer100g());
        cookingHistory.setTotalWeight(storage.getTotalWeight());
        cookingHistory.setWeightPerMeal(storage.getWeightPerMeal());
        cookingHistory.setLowStockThreshold(storage.getLowStockThreshold());

        if (storage.getCreatedAt() != null) {
            cookingHistory.setCreatedAt(storage.getCreatedAt());
        } else {
            cookingHistory.setCreatedAt(Instant.now());
        }

        return cookingHistory;
    }

    public static CookingHistoryResponse toResponse(CookingHistory cookingHistory) {
        return CookingHistoryResponse.builder()
                .id(cookingHistory.getId())
                .name(cookingHistory.getName())
                .totalWeight(cookingHistory.getTotalWeight())
                .weightPerMeal(cookingHistory.getWeightPerMeal())
                .nutritionPer100g(cookingHistory.getNutritionPer100g())
                .lowStockThreshold(cookingHistory.getLowStockThreshold())
                .createdAt(cookingHistory.getCreatedAt())
                .build();
    }
}
