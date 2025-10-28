package com.automagic.foodtracker.service.storage;

import com.automagic.foodtracker.dto.request.storage.CreateStorageRequest;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.entity.Storage;

import java.util.Collection;

public interface StorageService {
    Storage registerStorage(String userId, CreateStorageRequest request);
    Nutrition getNutrition(String storageId, String userId);
    void deleteStorage(String userId, String storageId);
    Collection<Storage> getStorage(String userId123);
}
