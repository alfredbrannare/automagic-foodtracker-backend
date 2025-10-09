package com.automagic.foodtracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Objects;

@Data
@NoArgsConstructor
@Document(collection = "meals")
public class Meal {
    @Id
    private String id;
    private String userId;
    private String storageId;
    private String name;
    private double weight;
    private Nutrition nutrition;
    private Instant consumedAt;

    //Helper Methods
    public boolean isFromStorage() {
        return storageId != null;
    }

}
