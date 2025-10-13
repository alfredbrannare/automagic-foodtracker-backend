package com.automagic.foodtracker.service.meal;

import com.automagic.foodtracker.entity.Meal;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.repository.meal.MealRepository;
import com.automagic.foodtracker.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final StorageService storageService;

    public Nutrition calculateDailyNutrition(String userId, Instant from, Instant to) {
        Collection<Meal> meals = mealRepository.findByUserIdAndConsumedAtBetween(userId, from, to);

        double totalProtein = meals.stream().mapToDouble(m -> m.getNutrition().protein()).sum();
        double totalCarbs = meals.stream().mapToDouble(m -> m.getNutrition().carbs()).sum();
        double totalFat = meals.stream().mapToDouble(m -> m.getNutrition().fat()).sum();
        double totalKcal = meals.stream().mapToDouble(m -> m.getNutrition().kcal()).sum();

        return new Nutrition(totalProtein, totalCarbs, totalFat, totalKcal);
    }

    @Override
    public Meal registerMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    @Override
    public Collection<Meal> getAllMeals(String userId, Instant from, Instant to) {
        return mealRepository.findByUserIdAndConsumedAtBetween(userId, from, to);
    }
}
