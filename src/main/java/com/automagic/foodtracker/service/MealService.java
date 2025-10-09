package com.automagic.foodtracker.service;

import com.automagic.foodtracker.entity.Meal;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.repository.MealRepository;
import com.automagic.foodtracker.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final StorageService storageService;

    public Collection<Nutrition> calculateDailyNutrition (String userId, Instant from, Instant to) {
                 return mealRepository.findByUserIdAndConsumedAtBetween(userId, from, to)
                         .stream()
                         .map(meal -> meal.getStorageId() != null
                         ? storageService.getNutrition(userId, meal.getStorageId()).scale(meal.getWeight())
                         : meal.getNutrition().scale(meal.getWeight()))
                         .toList();
    }

}
