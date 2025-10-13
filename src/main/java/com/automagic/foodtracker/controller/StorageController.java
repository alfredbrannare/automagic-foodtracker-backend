package com.automagic.foodtracker.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class StorageController {
    @GetMapping("/api/storage")
    public String getStorages(Principal principal) {

        return "Hello Storage";
    }
}

//Principal principal - Injectas som dependency
