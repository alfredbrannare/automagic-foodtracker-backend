package com.automagic.foodtracker.dto.response.user;

import com.automagic.foodtracker.entity.Goals;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String username;
    private String email;
    private Goals goals;
}
