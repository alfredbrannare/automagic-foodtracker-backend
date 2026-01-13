package com.automagic.foodtracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
    insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "user_id", nullable = false)
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
