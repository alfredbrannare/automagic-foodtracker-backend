package com.automagic.foodtracker.service.user;

import com.automagic.foodtracker.entity.User;

public interface UserService {
    User createUser(String name, String email, String password);
    User findByUsername(String username);
    boolean verifyPassword(String password, String hashedPassword);
    boolean usernameExists(String username);
    boolean emailExists(String email);
    void deleteUser(String userId);
    User findById(String userId);
}
