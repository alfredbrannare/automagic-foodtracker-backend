package com.automagic.foodtracker.service.user;

import com.automagic.foodtracker.entity.Goals;
import com.automagic.foodtracker.entity.User;

public interface UserService {
    User createUser(String name, String email, String password);
    User findByUsername(String username);
    boolean usernameExists(String username);
    boolean emailExists(String email);
    void deleteUser(String userId);
    User findById(String userId);
    Goals updateGoals(String userId, Goals goals);
    Goals getGoals(String userId);
}
