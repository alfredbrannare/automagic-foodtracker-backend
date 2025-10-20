package com.automagic.foodtracker.controller.storage;

import com.automagic.foodtracker.dto.request.storage.CreateStorageRequest;
import com.automagic.foodtracker.dto.response.storage.StorageResponse;
import com.automagic.foodtracker.entity.Storage;
import com.automagic.foodtracker.service.storage.StorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;

    @PostMapping()
    public ResponseEntity<StorageResponse> addStorage(
            @AuthenticationPrincipal String userId,
            @Valid @RequestBody CreateStorageRequest request) {

        Storage saved = storageService.registerStorage(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                StorageResponse.builder()
                        .id(saved.getId())
                        .name(saved.getName())
                        .totalWeight(saved.getTotalWeight())
                        .consumedWeight(saved.getConsumedWeight())
                        .weightPerMeal(saved.getWeightPerMeal())
                        .lowStockThreshold(saved.getLowStockThreshold())
                        .createdAt(saved.getCreatedAt())
                        .lowStock(saved.isLowStock())
                        .build()
        );
    }
}