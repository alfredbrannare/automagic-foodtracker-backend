package com.automagic.foodtracker.service.token;

import com.automagic.foodtracker.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createToken(String userId, String token, long durationMillis);
    Optional<RefreshToken> findByToken(String token);
    void deleteToken(String token);
    void deleteByUserId(String userId);
}
