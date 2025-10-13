package com.automagic.foodtracker.service.storage;

import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.exception.StorageNotFoundException;
import com.automagic.foodtracker.repository.storage.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final StorageRepository storageRepository;

    public Nutrition getNutrition(String storageId, String userId) {
        return storageRepository.findByIdAndUserId(storageId, userId)
                .orElseThrow(() -> new StorageNotFoundException(storageId, userId))
                .getNutritionPer100g();
    }
}
