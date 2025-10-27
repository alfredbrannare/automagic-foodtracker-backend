package com.automagic.foodtracker.service.storage;

import com.automagic.foodtracker.dto.request.storage.CreateStorageRequest;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.entity.Storage;

public interface StorageService {
    Storage registerStorage(String userId, CreateStorageRequest request);
    Nutrition getNutrition(String storageId, String userId);
    void deleteStorage(String userId, String storageId);
}
