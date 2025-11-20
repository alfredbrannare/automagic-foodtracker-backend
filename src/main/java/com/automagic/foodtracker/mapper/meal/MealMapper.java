package com.automagic.foodtracker.mapper.meal;

import com.automagic.foodtracker.dto.request.meal.CreateMealRequest;
import com.automagic.foodtracker.dto.request.meal.UpdateMealRequest;
import com.automagic.foodtracker.dto.response.meal.MealResponse;
import com.automagic.foodtracker.entity.Meal;

import java.time.Instant;

public class MealMapper {

    public static Meal toEntity(CreateMealRequest request) {
        Meal meal = new Meal();
        meal.setName(request.getName());
        meal.setWeight(request.getWeight());
        meal.setNutrition(request.getNutrition().scale(request.getWeight()));

        if (request.getConsumedAt() != null) {
            meal.setConsumedAt(request.getConsumedAt());
        } else {
            meal.setConsumedAt(Instant.now());
        }

        if (request.getStorageId() != null) {
            meal.setStorageId(request.getStorageId());
        }

        return meal;
    }

    public static Meal toEntity(String mealId, UpdateMealRequest request) {
        Meal meal = new Meal();

        meal.setId(mealId);
        meal.setName(request.getName());
        meal.setWeight(request.getWeight());
        meal.setNutrition(request.getNutrition());
        meal.setStorageId(request.getStorageId());
        meal.setConsumedAt(request.getConsumedAt());

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
