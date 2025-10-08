package com.automagic.foodtracker.entity;

public record Goals(
        double targetProtein,
        double targetCarbs,
        double targetFat,
        double targetCalories
        ) {}
