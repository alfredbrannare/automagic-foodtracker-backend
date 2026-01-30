package com.automagic.foodtracker.service.auth;

import com.automagic.foodtracker.dto.response.auth.AuthResponse;

public interface AuthService {
    void deleteUser(String userId);
    AuthResponse refreshToken(String refreshToken);
}
