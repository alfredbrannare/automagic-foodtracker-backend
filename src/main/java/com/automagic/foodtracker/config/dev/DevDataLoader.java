package com.automagic.foodtracker.config.dev;

import com.automagic.foodtracker.entity.Meal;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.entity.Storage;
import com.automagic.foodtracker.entity.User;
import com.automagic.foodtracker.repository.meal.MealRepository;
import com.automagic.foodtracker.repository.storage.StorageRepository;
import com.automagic.foodtracker.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@Configuration
@Profile("dev")
public class DevDataLoader {

    @Bean
    CommandLineRunner loadTestData(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder,
                                   MealRepository mealRepository,
                                   StorageRepository storageRepository) {

        return args -> {
            System.out.println("Loading test data...");

            //Users
            if (userRepository.findByUsername("user").isPresent()) {
                System.out.println("Test data already exists. Skipping...");
                return;
            }

            User user = new User();
            user.setUsername("user");
            user.setEmail("user@user.com");
            user.setPassword(passwordEncoder.encode("123"));
            userRepository.save(user);

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);

            //Storage
            Storage storage = new Storage();
            storage.setUserId(user.getId());
            storage.setName("Chicken");
            storage.setTotalWeight(2000.0);
            storage.setConsumedWeight(150.0);
            storage.setWeightPerMeal(150.0);
            storage.setNutritionPer100g(new Nutrition(32.1, 0, 3.2, 157.0));
            storage.setLowStockThreshold(450.0);
            storage.setCreatedAt(Instant.now());
            storageRepository.save(storage);

            //Meal
            Meal meal = new Meal();
            meal.setUserId(user.getId());
            meal.setName("Chicken");
            meal.setWeight(150.0);
            meal.setStorageId(storage.getId());
            meal.setConsumedAt(Instant.now());
            mealRepository.save(meal);

            Meal meal2 = new Meal();
            meal2.setName("Pizza");
            meal2.setWeight(150.0);
            meal2.setNutrition(new Nutrition(10.0, 20.0, 5.0, 150.0));
            meal2.setUserId(user.getId());
            meal2.setConsumedAt(Instant.now());
            mealRepository.save(meal2);

            System.out.println("Test data loaded successfully.");
        };
    }
}
