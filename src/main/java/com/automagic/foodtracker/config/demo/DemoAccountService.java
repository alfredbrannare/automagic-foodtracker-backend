package com.automagic.foodtracker.config.demo;

import com.automagic.foodtracker.entity.*;
import com.automagic.foodtracker.repository.meal.MealRepository;
import com.automagic.foodtracker.repository.storage.StorageRepository;
import com.automagic.foodtracker.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemoAccountService implements CommandLineRunner {
    private final DemoAccountConfig config;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final StorageRepository storageRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (config.isEnabled()) {
            initializeDemoAccount();
        }
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void resetDemoAccount() {
        if (!config.isEnabled()) return;

        log.info("Resetting demo account...");

        Optional<User> demoUser = userRepository.findByUsername(config.getUsername());
        if (demoUser.isPresent()) {
            String userId = demoUser.get().getId();

            mealRepository.deleteAllByUserId(userId);
            storageRepository.deleteAllByUserId(userId);

            seedDemoData(demoUser.get());

            log.info("Demo account reset.");
        }
    }

    private void initializeDemoAccount() {
        Optional<User> existing = userRepository.findByUsername(config.getUsername());

        if (existing.isEmpty()) {
            log.info("Creating demo account...");

            User demoUser = new User();
            demoUser.setUsername(config.getUsername());
            demoUser.setEmail(config.getEmail());
            demoUser.setPassword(passwordEncoder.encode(config.getPassword()));
            demoUser.setGoals(new Goals(150.0, 250.0, 60.0, 2000.0));
            demoUser.setRole("ROLE_USER");

            User saved = userRepository.save(demoUser);
            seedDemoData(saved);

            log.info("Demo account created.");
        } else {
            log.info("Demo account already exists.");
        }
    }

    private void seedDemoData(User user) {
        String userId = user.getId();
        Instant now = Instant.now();

        Storage chickenStorage = new Storage();
        chickenStorage.setUserId(userId);
        chickenStorage.setName("Grilled Chicken Breast");
        chickenStorage.setTotalWeight(2000);
        chickenStorage.setConsumedWeight(450);
        chickenStorage.setWeightPerMeal(150);
        chickenStorage.setNutritionPer100g(new Nutrition(31.0, 0.0, 3.6, 165.0));
        chickenStorage.setLowStockThreshold(300);
        chickenStorage.setCreatedAt(now.minus(5, ChronoUnit.DAYS));
        chickenStorage.setLowStock(false);
        storageRepository.save(chickenStorage);

        Storage riceStorage = new Storage();
        riceStorage.setUserId(userId);
        riceStorage.setName("Brown Rice");
        riceStorage.setTotalWeight(1500);
        riceStorage.setConsumedWeight(900);
        riceStorage.setWeightPerMeal(200);
        riceStorage.setNutritionPer100g(new Nutrition(2.6, 23.0, 0.9, 111.0));
        riceStorage.setLowStockThreshold(400);
        riceStorage.setCreatedAt(now.minus(5, ChronoUnit.DAYS));
        riceStorage.setLowStock(false);
        storageRepository.save(riceStorage);

        Meal meal1 = new Meal();
        meal1.setUserId(userId);
        meal1.setName("Grilled Chicken Breast");
        meal1.setWeight(150);
        meal1.setNutrition(new Nutrition(46.5, 0.0, 5.4, 247.5));
        meal1.setStorageId(chickenStorage.getId());
        meal1.setConsumedAt(now.plus(9, ChronoUnit.HOURS));
        mealRepository.save(meal1);

        Meal meal2 = new Meal();
        meal2.setUserId(userId);
        meal2.setName("Brown Rice");
        meal2.setWeight(200);
        meal2.setNutrition(new Nutrition(5.2, 46.0, 1.8, 222.0));
        meal2.setStorageId(riceStorage.getId());
        meal2.setConsumedAt(now.plus(9, ChronoUnit.HOURS));
        mealRepository.save(meal2);

        log.info("Seeded demo data: 2 storage items, 2 meals");
    }
}
