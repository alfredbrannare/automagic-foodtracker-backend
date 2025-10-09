package com.automagic.foodtracker.dto.response;

import com.automagic.foodtracker.entity.Nutrition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MealResponse {
    private String id;
    private String name;
    private double weight;
    private Instant consumedAt;
    private Nutrition nutrition;
    private String storageId;
    private String storageName;
}
