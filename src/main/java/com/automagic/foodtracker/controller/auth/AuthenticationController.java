package com.automagic.foodtracker.controller.auth;

import com.automagic.foodtracker.dto.request.auth.LoginRequest;
import com.automagic.foodtracker.dto.request.auth.RegisterRequest;
import com.automagic.foodtracker.dto.response.auth.AuthResponse;
import com.automagic.foodtracker.exception.auth.InvalidCredentialsException;
import com.automagic.foodtracker.exception.auth.UserAlreadyExistsException;
import com.automagic.foodtracker.repository.user.UserRepository;
import com.automagic.foodtracker.security.AuthenticatedUser;
import com.automagic.foodtracker.service.auth.AuthService;
import com.automagic.foodtracker.service.user.UserService;
import com.automagic.foodtracker.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponse(null, null, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null, null, e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal AuthenticatedUser user) {
        authService.deleteUser(user.getUserId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
