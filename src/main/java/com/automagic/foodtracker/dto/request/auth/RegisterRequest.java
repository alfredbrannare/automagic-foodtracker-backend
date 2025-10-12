package com.automagic.foodtracker.dto.request.auth;

import com.automagic.foodtracker.entity.Goals;
import lombok.Getter;

@Getter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Goals goals;
}
