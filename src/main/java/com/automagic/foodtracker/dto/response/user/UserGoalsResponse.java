package com.automagic.foodtracker.dto.response.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserGoalsResponse {
    private double targetProtein;
    private double targetCarbs;
    private double targetFat;
    private double targetCalories;
}
