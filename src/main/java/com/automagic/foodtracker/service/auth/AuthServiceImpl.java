package com.automagic.foodtracker.service.auth;

import com.automagic.foodtracker.dto.request.auth.LoginRequest;
import com.automagic.foodtracker.dto.request.auth.RegisterRequest;
import com.automagic.foodtracker.dto.response.auth.AuthResponse;
import com.automagic.foodtracker.entity.User;
import com.automagic.foodtracker.exception.auth.InvalidCredentialsException;
import com.automagic.foodtracker.exception.auth.UserAlreadyExistsException;
import com.automagic.foodtracker.service.user.UserService;
import com.automagic.foodtracker.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userService.usernameExists(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already taken");
        }

        if (userService.emailExists(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        User user = userService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        return generateAuthResponse(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userService.findByUsername(request.getUsername());

        if (!userService.verifyPassword(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        return generateAuthResponse(user);
    }

    @Override
    public void deleteUser(String userId) {
        userService.deleteUser(userId);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new InvalidCredentialsException("Invalid or expired refresh token");
        }

        String userId = jwtUtil.extractUserId(refreshToken);
        User user = userService.findById(userId);

        return generateAuthResponse(user);
    }

    private AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtUtil.generateToken(user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        return new AuthResponse(accessToken, refreshToken, "Success");
    }
}
