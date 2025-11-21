package com.automagic.foodtracker.controller.user;

import com.automagic.foodtracker.dto.request.user.UpdateUserGoalsRequest;
import com.automagic.foodtracker.dto.response.user.UserGoalsResponse;
import com.automagic.foodtracker.dto.response.user.UserResponse;
import com.automagic.foodtracker.entity.Goals;
import com.automagic.foodtracker.mapper.user.UserMapper;
import com.automagic.foodtracker.security.AuthenticatedUser;
import com.automagic.foodtracker.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.automagic.foodtracker.dto.request.user.UpdateUserRequest;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        user.getUserId();
        return null;
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateUser(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody UpdateUserRequest request
    ) {

        user.getUserId();
        return null;
    }

    @PutMapping("/me/goals")
    public ResponseEntity<UserGoalsResponse> updateGoals(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody UpdateUserGoalsRequest request
    ) {

        Goals goals = UserMapper.toGoalsEntity(request);
        userService.updateGoals(user.getUserId(), goals);
        UserGoalsResponse response = UserMapper.toGoalsResponse(goals);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/me/password")
    public ResponseEntity<UserResponse> updatePassword(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody UpdateUserRequest request
    ) {

        user.getUserId();
        return null;
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return null;
    }

}
