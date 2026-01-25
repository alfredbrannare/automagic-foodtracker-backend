package com.automagic.foodtracker.dto.response.cookinghistory;

import com.automagic.foodtracker.entity.Nutrition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CookingHistoryResponse {
    private String id;
    private String name;
    private double totalWeight;
    private double weightPerMeal;
    private Nutrition nutritionPer100g;
    private double lowStockThreshold;
    private Instant createdAt;
}
