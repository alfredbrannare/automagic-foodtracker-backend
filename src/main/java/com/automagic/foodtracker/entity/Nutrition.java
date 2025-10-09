package com.automagic.foodtracker.entity;

public record Nutrition(
    double protein,
    double carbs,
    double fat,
    double kcal
) {
    public Nutrition scale(double weightInGrams) {
        double multiplier = weightInGrams / 100;
        return new Nutrition(
                round(protein * multiplier),
                round(carbs * multiplier),
                round(fat * multiplier),
                round(kcal * multiplier)
        );
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

}
