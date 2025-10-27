package com.automagic.foodtracker.service.auth;

import com.automagic.foodtracker.dto.request.auth.LoginRequest;
import com.automagic.foodtracker.dto.request.auth.RegisterRequest;
import com.automagic.foodtracker.dto.response.auth.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void deleteUser(String userId);
    AuthResponse refreshToken(String refreshToken);
}
