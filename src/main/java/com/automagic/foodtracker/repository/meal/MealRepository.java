package com.automagic.foodtracker.repository.meal;

import com.automagic.foodtracker.entity.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface MealRepository extends MongoRepository<Meal, String> {
    Collection<Meal> findByUserIdAndConsumedAtBetween(String userId, Instant from, Instant to);
    Collection<Meal> findByStorageId(String storageId);
    Collection<Meal> findByUserIdAndStorageIdIsNull(String userId);
    long countByUserIdAndConsumedAtBetween(String userId, Instant from, Instant to);
}
