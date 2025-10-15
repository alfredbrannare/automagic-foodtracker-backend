package com.automagic.foodtracker.service.meal;


import com.automagic.foodtracker.entity.Meal;
import com.automagic.foodtracker.entity.Nutrition;

import java.time.Instant;
import java.util.Collection;

public interface MealService {
    Meal registerMeal(Meal newMeal);
    Collection<Meal> getAllMeals(String userId, Instant from, Instant to);
    Nutrition getDailyNutrition(String userId, Instant from, Instant to);
}
