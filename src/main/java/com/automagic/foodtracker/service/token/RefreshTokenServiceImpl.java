package com.automagic.foodtracker.service.token;

import com.automagic.foodtracker.entity.RefreshToken;
import com.automagic.foodtracker.repository.token.RefreshTokenRepository;
import com.automagic.foodtracker.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createToken(String userId, String token, long durationMillis) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setToken(token);
        refreshToken.setExpiresAt(Instant.now().plusMillis(durationMillis));

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Override
    public void deleteByUserId(String userId) {
        refreshTokenRepository.deleteAllByUserId(userId);
    }
}
