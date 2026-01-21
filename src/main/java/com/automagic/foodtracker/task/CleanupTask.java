package com.automagic.foodtracker.task;

import com.automagic.foodtracker.repository.meal.MealRepository;
import com.automagic.foodtracker.repository.storage.StorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanupTask {
    private final MealRepository mealRepository;

    @Scheduled(cron = "0 0 0 * * SUN")
    public void clearOldMeals() {
        Instant cutoff = Instant.now().minus(14, ChronoUnit.DAYS);
        log.info("Starting cleanup for data older than {}", cutoff);

        int deletedMeals = mealRepository.deleteByConsumedAtBefore(cutoff);

        log.info("Cleanup complete. Deleted {} old meals", deletedMeals);
    }

}
