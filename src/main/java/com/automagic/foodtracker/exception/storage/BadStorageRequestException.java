package com.automagic.foodtracker.exception.storage;

public class BadStorageRequestException extends RuntimeException {
    public BadStorageRequestException(String message) {
        super(message);
    }
}
