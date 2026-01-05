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
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserResponse> getUser(
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        user.getUserId();
        return null;
    }

    @PutMapping()
    public ResponseEntity<UserResponse> updateUser(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody UpdateUserRequest request
    ) {

        user.getUserId();
        return null;
    }

    @GetMapping("/goals")
    public ResponseEntity<UserGoalsResponse> getGoals(
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        Goals goals = userService.getGoals(user.getUserId());
        UserGoalsResponse response = UserMapper.toGoalsResponse(goals);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/goals")
    public ResponseEntity<UserGoalsResponse> updateGoals(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody UpdateUserGoalsRequest request
    ) {

        Goals goals = UserMapper.toGoalsEntity(request);
        userService.updateGoals(user.getUserId(), goals);
        UserGoalsResponse response = UserMapper.toGoalsResponse(goals);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/password")
    public ResponseEntity<UserResponse> updatePassword(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody UpdateUserRequest request
    ) {

        user.getUserId();
        return null;
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return null;
    }

}
