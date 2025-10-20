package com.automagic.foodtracker.exception.storage;

public class StorageNotFoundException extends RuntimeException {
    public StorageNotFoundException(String message) {
        super(message);
    }

    public StorageNotFoundException(String storageId, String userId) {
        super("Storage with id " + storageId + " not found: " + userId);
    }
}
