package com.automagic.foodtracker.controller;

import com.automagic.foodtracker.dto.request.meal.CreateMealRequest;
import com.automagic.foodtracker.dto.response.MealResponse;
import com.automagic.foodtracker.entity.Meal;
import com.automagic.foodtracker.service.meal.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealsController {
    private final MealService mealService;

    @PostMapping
    public ResponseEntity<MealResponse> addMeal(
            @AuthenticationPrincipal String userId,
            @RequestBody CreateMealRequest request) {
        Meal saved = mealService.registerMeal(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                MealResponse.builder()
                        .id(saved.getId())
                        .name(saved.getName())
                        .weight(saved.getWeight())
                        .consumedAt(saved.getConsumedAt())
                        .nutrition(saved.getNutrition())
                        .storageId(saved.getStorageId())
                        .storageName(null)
                        .build()
        );
    }

}
