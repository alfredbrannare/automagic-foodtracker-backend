package com.automagic.foodtracker.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@Getter
@RequiredArgsConstructor
public class AuthenticatedUser implements Principal {
    private final String userId;

    @Override
    public String getName() {
        return userId;
    }
}
