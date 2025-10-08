package com.automagic.foodtracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {
    @GetMapping("/storage")
    public String getStorages() {
        return "Hello Affe";
    }
}