package com.automagic.foodtracker.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record Goals(
        double targetProtein,
        double targetCarbs,
        double targetFat,
        double targetCalories
        ) {}
