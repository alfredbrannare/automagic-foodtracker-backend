package com.automagic.foodtracker.entity;

import java.time.Instant;

public record Meal(
        String id,
        String useId,
        String storageId,
        String name,
        double weight,
        Nutrition nutrition,
        Instant consumedAt
) {}
