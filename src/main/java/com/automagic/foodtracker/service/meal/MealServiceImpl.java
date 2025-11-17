package com.automagic.foodtracker.service.meal;

import com.automagic.foodtracker.dto.request.meal.CreateMealRequest;
import com.automagic.foodtracker.entity.Meal;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.entity.Storage;
import com.automagic.foodtracker.repository.meal.MealRepository;
import com.automagic.foodtracker.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

import static java.lang.Math.round;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final StorageService storageService;

    @Override
    public Nutrition getDailyNutrition(String userId, Instant from, Instant to) {
        Collection<Meal> meals = mealRepository.findByUserIdAndConsumedAtBetween(userId, from, to);

        double totalProtein = meals.stream().mapToDouble(m -> m.getNutrition().protein()).sum();
        double totalCarbs = meals.stream().mapToDouble(m -> m.getNutrition().carbs()).sum();
        double totalFat = meals.stream().mapToDouble(m -> m.getNutrition().fat()).sum();
        double totalKcal = meals.stream().mapToDouble(m -> m.getNutrition().kcal()).sum();

        return new Nutrition(round(totalProtein), round(totalCarbs), round(totalFat), round(totalKcal));
    }

    @Override
    public Meal registerMeal(String userId, Meal meal) {
        meal.setUserId(userId);

        if (meal.getStorageId() != null) {
            Nutrition nutrition = storageService.getNutrition(meal.getStorageId(), userId);
            meal.setNutrition(nutrition.scale(meal.getWeight()));
            storageService.updateConsumedWeight(userId, meal.getStorageId(), meal.getWeight());
        }

        if (meal.getConsumedAt() == null) {
            meal.setConsumedAt(Instant.now());
        }

        if (meal.getNutrition() == null) {
            throw new IllegalArgumentException("Nutrition cannot be null");
        }

        return mealRepository.save(meal);
    }

    @Override
    public Collection<Meal> getMealsForUserBetween(String userId, Instant from, Instant to) {
        return mealRepository.findByUserIdAndConsumedAtBetween(userId, from, to);
    }

    @Override
    public void deleteMeal(String userId, String mealId) {
        mealRepository.findById(mealId).ifPresent(meal -> {
            if (!meal.getUserId().equals(userId)) {
                throw new AccessDeniedException("You are not allowed to delete this meal");
            }
            mealRepository.delete(meal);
        });
    }
}
