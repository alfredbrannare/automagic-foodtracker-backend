package com.automagic.foodtracker.service;

import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.exception.StorageNotFoundException;
import com.automagic.foodtracker.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;

    public Nutrition getNutrition(String storageId, String userId) {
        return storageRepository.findByIdAndUserId(storageId, userId)
                .orElseThrow(() -> new StorageNotFoundException(storageId, userId))
                .getNutritionPer100g();
    }
}
