package com.automagic.foodtracker.mapper.meal;

import com.automagic.foodtracker.dto.response.meal.MealResponse;
import com.automagic.foodtracker.entity.Meal;

public class MealMapper {
    public static MealResponse toResponse(Meal meal) {
        return MealResponse.builder()
                .id(meal.getId())
                .name(meal.getName())
                .weight(meal.getWeight())
                .consumedAt(meal.getConsumedAt())
                .nutrition(meal.getNutrition())
                .storageId(meal.getStorageId())
                .storageName(null)
                .build();
    }
}
