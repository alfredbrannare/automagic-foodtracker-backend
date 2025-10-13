package com.automagic.foodtracker.service.meal;


import com.automagic.foodtracker.entity.Meal;

import java.time.Instant;
import java.util.Collection;

public interface MealService {
    Meal registerMeal(Meal newMeal);
    Collection<Meal> getAllMeals(String userId, Instant from, Instant to);
}
