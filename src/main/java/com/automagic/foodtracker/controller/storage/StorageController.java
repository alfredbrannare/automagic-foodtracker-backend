package com.automagic.foodtracker.controller.storage;

import com.automagic.foodtracker.dto.request.storage.CreateStorageRequest;
import com.automagic.foodtracker.dto.request.storage.UpdateStorageRequest;
import com.automagic.foodtracker.dto.response.storage.StorageResponse;
import com.automagic.foodtracker.entity.Storage;
import com.automagic.foodtracker.mapper.storage.StorageMapper;
import com.automagic.foodtracker.security.AuthenticatedUser;
import com.automagic.foodtracker.service.storage.StorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;

    @PostMapping()
    public ResponseEntity<StorageResponse> addStorage(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody CreateStorageRequest request) {

        Storage storage = StorageMapper.toEntity(request);
        Storage saved = storageService.registerStorage(user.getUserId(), storage);
        StorageResponse response = StorageMapper.toResponse(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{storageId}")
    public ResponseEntity<Void> deleteStorage(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable String storageId) {

        storageService.deleteStorage(user.getUserId(), storageId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<StorageResponse>> getStorage(
            @AuthenticationPrincipal AuthenticatedUser user
    ) {

        List<StorageResponse> response = storageService.getStorage(user.getUserId())
                .stream()
                .map(StorageMapper::toResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{storageId}")
    public ResponseEntity<StorageResponse> updateStorage(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable String storageId,
            @Valid @RequestBody UpdateStorageRequest request
    ) {

        Storage storage = StorageMapper.toEntity(storageId, request);
        Storage updatedStorage = storageService.updateStorage(user.getUserId(), storage);
        StorageResponse response = StorageMapper.toResponse(updatedStorage);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}