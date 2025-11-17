package com.automagic.foodtracker.mapper.meal;

import com.automagic.foodtracker.dto.request.meal.CreateMealRequest;
import com.automagic.foodtracker.dto.response.meal.MealResponse;
import com.automagic.foodtracker.entity.Meal;

import java.time.Instant;

public class MealMapper {

    public static Meal toEntity(CreateMealRequest request) {
        Meal meal = new Meal();
        meal.setName(request.getName());
        meal.setWeight(request.getWeight());

        if (request.getNutrition() != null) {
        meal.setNutrition(request.getNutrition());
        }

        if (request.getConsumedAt() != null) {
            meal.setConsumedAt(request.getConsumedAt());
        }

        if (request.getStorageId() != null) {
            meal.setStorageId(request.getStorageId());
        }

        return meal;
    }

    public static MealResponse toResponse(Meal meal) {
        return MealResponse.builder()
                .id(meal.getId())
                .name(meal.getName())
                .weight(meal.getWeight())
                .consumedAt(meal.getConsumedAt())
                .nutrition(meal.getNutrition())
                .storageId(meal.getStorageId())
                .build();
    }

}
