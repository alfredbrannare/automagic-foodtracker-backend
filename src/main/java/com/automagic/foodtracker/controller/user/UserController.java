package com.automagic.foodtracker.controller.user;

import com.automagic.foodtracker.dto.request.user.UpdateGoalsRequest;
import com.automagic.foodtracker.dto.response.user.UserResponse;
import com.automagic.foodtracker.security.AuthenticatedUser;
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
    public ResponseEntity<UserResponse> updateGoals(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody UpdateGoalsRequest request
    ) {

        user.getUserId();
        return null;
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
