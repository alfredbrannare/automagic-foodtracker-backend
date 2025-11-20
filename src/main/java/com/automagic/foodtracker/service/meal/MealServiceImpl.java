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
            Storage storage = storageService.getStorageById(userId, meal.getStorageId());

            meal.setName(storage.getName());
            meal.setNutrition(storage.getNutritionPer100g().scale(meal.getWeight()));

            storageService.updateConsumedWeight(userId, storage.getId(), meal.getWeight());
        }

        return mealRepository.save(meal);
    }

    @Override
    public Collection<Meal> getMealsForUserBetween(String userId, Instant from, Instant to) {
        return mealRepository.findByUserIdAndConsumedAtBetween(userId, from, to);
    }

    @Override
    public void deleteMeal(String userId, String mealId) {
        Meal existing = mealRepository.findById(mealId)
                        .orElseThrow(() -> new RuntimeException("Meal not found"));

            if (!existing.getUserId().equals(userId)) {
                throw new AccessDeniedException("You are not allowed to delete this meal");
            }

            if (existing.getStorageId() != null) {
                storageService.updateConsumedWeight(userId, existing.getStorageId(), -existing.getWeight());
            }

            mealRepository.delete(existing);
    }

    @Override
    public Meal updateMeal(String userId, Meal updates) {
        Meal existing = mealRepository.findById(updates.getId())
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        if (!existing.getUserId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to update this meal");
        }

        String existingStorageId = existing.getStorageId();
        double existingWeight = existing.getWeight();
        String newStorageId = updates.getStorageId();
        double newWeight = updates.getWeight();

        existing.setWeight(newWeight);
        existing.setConsumedAt(updates.getConsumedAt());

        if (newStorageId != null) {
            Storage storage = storageService.getStorageById(userId, newStorageId);
            existing.setName(storage.getName());
            existing.setNutrition(storage.getNutritionPer100g().scale(newWeight));
        }
        else {
            existing.setName(updates.getName());
            existing.setNutrition(updates.getNutrition().scale(existingWeight));
        }

        if (existingStorageId == null && newStorageId != null) {
            storageService.updateConsumedWeight(userId, newStorageId, newWeight);
        }
        else if (existingStorageId != null && newStorageId == null) {
            storageService.updateConsumedWeight(userId, existingStorageId, -existingWeight);
        }
        else if (existingStorageId != null && newStorageId != null) {
            if (!existingStorageId.equals(newStorageId)) {
                storageService.updateConsumedWeight(userId, existingStorageId, -existingWeight);
                storageService.updateConsumedWeight(userId, newStorageId, newWeight);
            }
            else if (newWeight != existingWeight) {
                double weightDifference = newWeight - existingWeight;
                storageService.updateConsumedWeight(userId, newStorageId, weightDifference);
            }
        }

        existing.setStorageId(newStorageId);
        return mealRepository.save(existing);
    }

}
