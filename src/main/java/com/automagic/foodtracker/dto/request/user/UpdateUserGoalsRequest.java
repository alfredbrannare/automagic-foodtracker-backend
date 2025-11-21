package com.automagic.foodtracker.dto.request.user;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserGoalsRequest {
    @Min(0)
    private double targetProtein;

    @Min(0)
    private double targetCarbs;

    @Min(0)
    private double targetFat;

    @Min(0)
    private double targetCalories;
}
