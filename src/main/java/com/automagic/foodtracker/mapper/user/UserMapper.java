package com.automagic.foodtracker.mapper.user;

import com.automagic.foodtracker.dto.request.user.UpdateUserGoalsRequest;
import com.automagic.foodtracker.dto.response.user.UserGoalsResponse;
import com.automagic.foodtracker.dto.response.user.UserResponse;
import com.automagic.foodtracker.entity.Goals;
import com.automagic.foodtracker.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return null;
    }

    public static Goals toGoalsEntity(UpdateUserGoalsRequest request) {
        System.out.println("toGoalsEntity: " + request.getTargetProtein() + ", " + request.getTargetCalories() + ", " + request.getTargetCarbs() + ", " + request.getTargetCalories());
        return new Goals(
                request.getTargetProtein(),
                request.getTargetCarbs(),
                request.getTargetFat(),
                request.getTargetCalories());
    }

    public static UserGoalsResponse toGoalsResponse(Goals goals) {
        if (goals == null) {
            return UserGoalsResponse.builder()
                    .targetProtein(0.0)
                    .targetCarbs(0.0)
                    .targetFat(0.0)
                    .targetCalories(0.0)
                    .build();
        }

        return UserGoalsResponse.builder()
                .targetProtein(goals.targetProtein())
                .targetCarbs(goals.targetCarbs())
                .targetFat(goals.targetFat())
                .targetCalories(goals.targetCalories())
                .build();
    }

}
