package com.automagic.foodtracker.service.auth;

import com.automagic.foodtracker.dto.response.auth.AuthResponse;
import com.automagic.foodtracker.entity.User;
import com.automagic.foodtracker.exception.auth.InvalidCredentialsException;
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
    public void deleteUser(String userId) {
        userService.deleteUser(userId);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new InvalidCredentialsException("Invalid or expired refresh token");
        }

        if (!jwtUtil.isRefreshToken(refreshToken)) {
            throw new InvalidCredentialsException("Invalid token type");
        }

        String userId = jwtUtil.extractUserId(refreshToken);
        User user = userService.findById(userId);

        return generateAuthResponse(user);
    }

    private AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtUtil.generateToken(user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        return new AuthResponse(accessToken, refreshToken);
    }
}
