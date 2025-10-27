package com.automagic.foodtracker.controller.storage;

import com.automagic.foodtracker.dto.request.storage.CreateStorageRequest;
import com.automagic.foodtracker.dto.response.storage.StorageResponse;
import com.automagic.foodtracker.entity.Storage;
import com.automagic.foodtracker.security.AuthenticatedUser;
import com.automagic.foodtracker.service.storage.StorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;

    @PostMapping()
    public ResponseEntity<StorageResponse> addStorage(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody CreateStorageRequest request) {

        Storage saved = storageService.registerStorage(user.getUserId(), request);

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

    @DeleteMapping("/{storageId}")
    public ResponseEntity<Void> deleteStorage(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable String storageId) {

        storageService.deleteStorage(user.getUserId(), storageId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}