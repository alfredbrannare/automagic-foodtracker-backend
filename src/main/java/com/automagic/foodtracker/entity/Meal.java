package com.automagic.foodtracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "meal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column
    private String storageId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double weight;

    @Embedded
    private Nutrition nutrition;

    @Column(nullable = false)
    private Instant consumedAt;

    //Helper Methods
    public boolean isFromStorage() {
        return storageId != null;
    }

}
