package com.automagic.foodtracker.service.storage;

import com.automagic.foodtracker.entity.Nutrition;

public interface StorageService {
    Nutrition getNutrition(String storageId, String userId);
}
