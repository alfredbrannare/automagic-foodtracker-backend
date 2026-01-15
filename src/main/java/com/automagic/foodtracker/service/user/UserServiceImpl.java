package com.automagic.foodtracker.service.user;

import com.automagic.foodtracker.dto.request.auth.RegisterRequest;
import com.automagic.foodtracker.entity.Goals;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.entity.User;
import com.automagic.foodtracker.exception.user.UserNotFoundException;
import com.automagic.foodtracker.repository.user.UserRepository;
import com.automagic.foodtracker.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(String username, String email, String plainPassword) {
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(plainPassword));
    user.setGoals(new Goals(150, 250, 60, 2000));
    user.setRole("USER");

    return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> UserNotFoundException.byUsername(username));
    }

    @Override
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.findById(userId).ifPresent(user -> {
            if ("demo".equals(user.getUsername())) {
                return;
            }
            userRepository.deleteById(userId);
        });
    }

    @Override
    public User findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));
    }

    @Transactional
    @Override
    public Goals updateGoals(String userId, Goals goals) {
        System.out.println("Entering findById or elseThrow with id: " + userId);
        User existing = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));

        System.out.println("Found user: " + existing);
        System.out.println("Is the existing user the same as the userId? " + existing.getId() + " == " + userId);
        if (!existing.getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to update this user");
        }

        System.out.println("Updating goals: " + goals);
        existing.setGoals(goals);

        System.out.println("Returning goals: " + existing.getGoals());
        return userRepository.save(existing).getGoals();
    }

    @Override
    public Goals getGoals(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId)).getGoals();
    }

}
