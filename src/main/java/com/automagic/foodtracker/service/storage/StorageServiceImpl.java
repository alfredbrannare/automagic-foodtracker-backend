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
import java.util.Optional;

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
    public Collection<Storage> getStorage(String userId) {
        return storageRepository.findByUserId(userId);
    }

    @Override
    public Storage getStorageById(String userId, String storageId) {
        return storageRepository.findByIdAndUserId(storageId, userId)
                .orElseThrow(() -> new StorageNotFoundException(storageId, userId));
    }

    public Storage updateConsumedWeight(String userId, String storageId, double weightChange) {
        Optional<Storage> existing = storageRepository.findByIdAndUserId(storageId, userId);

        if (existing.isEmpty()) {
            throw new StorageNotFoundException(storageId, userId);
        }

        double newConsumedWeight = existing.get().getConsumedWeight() + weightChange;

        if (newConsumedWeight > existing.get().getTotalWeight()) {
            throw new BadStorageRequestException("Consumed weight cannot be greater than total weight");
        }

        if (newConsumedWeight < 0) {
            throw new BadStorageRequestException("Consumed weight cannot be negative");
        }

        if (weightChange == 0) {
            return existing.get();
        }

        existing.get().setConsumedWeight(newConsumedWeight);
        return storageRepository.save(existing.get());
    }
}
