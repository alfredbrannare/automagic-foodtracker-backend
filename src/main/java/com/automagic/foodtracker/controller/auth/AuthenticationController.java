package com.automagic.foodtracker.controller.auth;

import com.automagic.foodtracker.dto.request.auth.LoginRequest;
import com.automagic.foodtracker.dto.request.auth.RefreshRequest;
import com.automagic.foodtracker.dto.request.auth.RegisterRequest;
import com.automagic.foodtracker.dto.response.auth.AuthResponse;
import com.automagic.foodtracker.security.AuthenticatedUser;
import com.automagic.foodtracker.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal AuthenticatedUser user) {
        authService.deleteUser(user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshRequest request) {
        AuthResponse response = authService.refreshToken(request.refreshToken());
        return ResponseEntity.ok(response);
    }
}
