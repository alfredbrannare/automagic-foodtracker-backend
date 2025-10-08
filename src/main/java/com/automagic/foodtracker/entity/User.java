package com.automagic.foodtracker.entity;

import java.time.Instant;

public record User(
        String id,
        String username,
        String email,
        Instant createdAt,
        Goals goals
) {}
