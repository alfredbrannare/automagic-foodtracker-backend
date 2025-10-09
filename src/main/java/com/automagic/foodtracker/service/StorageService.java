package com.automagic.foodtracker.service;

import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;

    public Nutrition getNutrition(String userId, String storageId) {
        return storageRepository.findByIdAndUserId(userId, storageId).get().getNutritionPer100g();
    }
}
