package com.automagic.foodtracker.service.storage;

import com.automagic.foodtracker.dto.request.storage.CreateStorageRequest;
import com.automagic.foodtracker.entity.Meal;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.entity.Storage;
import com.automagic.foodtracker.exception.storage.BadStorageRequestException;
import com.automagic.foodtracker.exception.storage.StorageNotFoundException;
import com.automagic.foodtracker.repository.storage.StorageRepository;
import com.automagic.foodtracker.service.meal.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
    private final StorageRepository storageRepository;
    private final MealService mealService;

    public StorageServiceImpl(StorageRepository storageRepository, @Lazy MealService mealService) {
        this.storageRepository = storageRepository;
        this.mealService = mealService;
    }

    @Transactional
    @Override
    public Storage registerStorage(String userId, Storage storage) {
        storage.setUserId(userId);
        return storageRepository.save(storage);
    }

    @Transactional(readOnly = true)
    @Override
    public Nutrition getNutrition(String storageId, String userId) {
        return storageRepository.findByIdAndUserId(storageId, userId)
                .orElseThrow(() -> new StorageNotFoundException(storageId, userId))
                .getNutritionPer100g();
    }

    @Transactional
    @Override
    public void deleteStorage(String userId, String storageId) {
        Storage existing = storageRepository.findByIdAndUserId(storageId, userId)
                .orElseThrow(() -> new StorageNotFoundException(storageId, userId));

        if (!existing.getUserId().equals(userId)) {
            throw new BadStorageRequestException("You are not allowed to delete this storage");
        }

        Instant twoWeeksAgo = Instant.now().minus(14, ChronoUnit.DAYS);
        List<Meal> linkedMeals = mealService.findMealsByUserAndStorageSince(userId, storageId, twoWeeksAgo);
        for (Meal meal : linkedMeals) {
            meal.setStorageId(null);
            mealService.saveMeal(meal);
        }

        storageRepository.deleteById(storageId);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Storage> getStorage(String userId) {
        return storageRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public Storage updateStorage(String userId, Storage updates) {
        Storage existing = storageRepository.findById(updates.getId())
                .orElseThrow(() -> new StorageNotFoundException(updates.getId(), userId));

        if (!existing.getUserId().equals(userId)) {
            throw new BadStorageRequestException("You are not allowed to update this storage");
        }

        existing.setName(updates.getName());
        existing.setTotalWeight(updates.getTotalWeight());
        existing.setWeightPerMeal(updates.getWeightPerMeal());
        existing.setLowStockThreshold(updates.getLowStockThreshold());
        existing.setNutritionPer100g(updates.getNutritionPer100g());

        Storage saved = storageRepository.save(existing);

        List<Meal> recentMeals = mealService.findMealsByUserAndStorage(userId, updates.getId());

        for (Meal meal : recentMeals) {
            mealService.updateMeal(userId, meal);
        }

        return saved;
    }

    @Transactional(readOnly = true)
    @Override
    public Storage getStorageById(String userId, String storageId) {
        return storageRepository.findByIdAndUserId(storageId, userId)
                .orElseThrow(() -> new StorageNotFoundException(storageId, userId));
    }

    @Transactional
    @Override
    public Storage updateConsumedWeight(String userId, String storageId, double weightChange) {
        Optional<Storage> existing = storageRepository.findByIdAndUserId(storageId, userId);

        if (existing.isEmpty()) {
            throw new StorageNotFoundException(storageId, userId);
        }

        double newConsumedWeight = existing.get().getConsumedWeight() + weightChange;

        if (newConsumedWeight < 0) {
            newConsumedWeight = 0;
        }

        if (weightChange == 0) {
            return existing.get();
        }

        if (newConsumedWeight >= existing.get().getTotalWeight()) {
            deleteStorage(userId, storageId);
            return existing.get();
        }

        existing.get().setConsumedWeight(newConsumedWeight);
        return storageRepository.save(existing.get());
    }
}
