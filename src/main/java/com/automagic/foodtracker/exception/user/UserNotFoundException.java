package com.automagic.foodtracker.exception.user;

public class UserNotFoundException extends RuntimeException {

    private UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException byId(String userId) {
        return new UserNotFoundException("User not found with id: " + userId);
    }

    public static UserNotFoundException byUsername(String username) {
        return new UserNotFoundException("User not found with username: " + username);
    }
}
