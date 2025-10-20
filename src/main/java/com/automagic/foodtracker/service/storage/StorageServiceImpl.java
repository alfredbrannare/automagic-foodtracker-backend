package com.automagic.foodtracker.service.storage;

import com.automagic.foodtracker.dto.request.storage.CreateStorageRequest;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.entity.Storage;
import com.automagic.foodtracker.exception.storage.BadStorageRequestException;
import com.automagic.foodtracker.exception.storage.StorageNotFoundException;
import com.automagic.foodtracker.repository.storage.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final StorageRepository storageRepository;

    @Override
    public Storage registerStorage(String userId, CreateStorageRequest request) {
        if (request.getLowStockThreshold() > request.getTotalWeight()) {
            throw new BadStorageRequestException("Low stock threshold cannot be greater than total weight");
        }

        if (request.getWeightPerMeal() > request.getTotalWeight()) {
            throw new BadStorageRequestException("Weight per meal cannot be greater than total weight");
        }

        Storage storage = new Storage();
        storage.setUserId(userId);
        storage.setName(request.getName());
        storage.setNutritionPer100g(request.getNutritionPer100g());
        storage.setTotalWeight(request.getTotalWeight());
        storage.setWeightPerMeal(request.getWeightPerMeal());
        storage.setLowStockThreshold(request.getLowStockThreshold());
        storage.setCreatedAt(request.getCreatedAt() != null ? request.getCreatedAt() : Instant.now());

        return storageRepository.save(storage);
    }

    public Nutrition getNutrition(String storageId, String userId) {
        return storageRepository.findByIdAndUserId(storageId, userId)
                .orElseThrow(() -> new StorageNotFoundException(storageId, userId))
                .getNutritionPer100g();
    }
}
