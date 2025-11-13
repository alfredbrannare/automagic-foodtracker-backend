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
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final StorageRepository storageRepository;

    @Override
    public Storage registerStorage(String userId, Storage storage) {
        storage.setUserId(userId);
        return storageRepository.save(storage);
    }

    public Nutrition getNutrition(String storageId, String userId) {
        return storageRepository.findByIdAndUserId(storageId, userId)
                .orElseThrow(() -> new StorageNotFoundException(storageId, userId))
                .getNutritionPer100g();
    }

    @Override
    public void deleteStorage(String userId, String storageId) {
        storageRepository.findByIdAndUserId(storageId, userId)
                .ifPresent(storageRepository::delete);
    }

    @Override
    public Collection<Storage> getStorage(String userId123) {
        return storageRepository.findByUserId(userId123);
    }
}
