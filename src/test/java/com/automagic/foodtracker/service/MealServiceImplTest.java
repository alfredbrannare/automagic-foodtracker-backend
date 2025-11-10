//package com.automagic.foodtracker.service;
//
//import com.automagic.foodtracker.dto.request.meal.CreateMealRequest;
//import com.automagic.foodtracker.entity.Meal;
//import com.automagic.foodtracker.entity.Nutrition;
//import com.automagic.foodtracker.entity.User;
//import com.automagic.foodtracker.repository.meal.MealRepository;
//import com.automagic.foodtracker.repository.storage.StorageRepository;
//import com.automagic.foodtracker.repository.user.UserRepository;
//import com.automagic.foodtracker.service.meal.MealService;
//import com.automagic.foodtracker.service.meal.MealServiceImpl;
//import com.automagic.foodtracker.service.storage.StorageService;
//import com.automagic.foodtracker.service.storage.StorageServiceImpl;
//import com.automagic.foodtracker.service.user.UserService;
//import com.automagic.foodtracker.service.user.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.time.Instant;
//import java.util.Collection;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Testcontainers
//@DataJpaTest
//@Import({StorageServiceImpl.class, MealServiceImpl.class, UserServiceImpl.class})
//public class MealServiceImplTest {
//
//    // --- Test Container Setup ---
//    @TestConfiguration
//    static class TestConfig {
//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//    }
//
//    @Container
//    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
//            .withDatabaseName("foodtracker")
//            .withUsername("user")
//            .withPassword("password");
//
//    @DynamicPropertySource
//    static void setProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", postgresContainer::getUsername);
//        registry.add("spring.datasource.password", postgresContainer::getPassword);
//    }
//
//    // --- Service and Repository Autowiring ---
//    private User testUser;
//    private User otherTestUser;
//
//    @Autowired
//    private MealRepository mealRepository;
//    @Autowired
//    private MealService mealService;
//
//    @Autowired
//    private StorageRepository storageRepository;
//    @Autowired
//    private StorageService storageService;
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserService userService;
//
//
//    // --- Test Lifecycle Methods ---
//
//    @BeforeEach
//    void cleanDb() {
//        mealRepository.deleteAll();
//        storageRepository.deleteAll();
//        userRepository.deleteAll();
//
//        User userToSave = createTestUser("user", "test@user.com", "userpassword");
//        this.testUser = userRepository.save(userToSave);
//
//        User otherUserToSave = createTestUser("otherTestUser", "otherTest@user.com", "testpassword");
//        this.otherTestUser = userRepository.save(otherUserToSave);
//    }
//
//
//    // --- Helper Methods ---
//
//    private Meal createTestMeal(String userId, String name, double weight, Nutrition nutrition, Instant consumedAt) {
//        Meal meal = new Meal();
//        meal.setUserId(userId);
//        meal.setName(name);
//        meal.setWeight(weight);
//        meal.setNutrition(nutrition);
//        meal.setConsumedAt(consumedAt);
//        return meal;
//    }
//
//    private User createTestUser(String username, String email, String password) {
//        User user = new User();
//        user.setUsername(username);
//        user.setEmail(email);
//        user.setPassword(password);
//        return user;
//    }
//
//
//    // --- Tests ---
//
//    @Test
//    @DisplayName("registerMeal() should save meal to the database with the correct userId")
//    void registerMealSavesMealCorrectly() {
//        final Instant fixedTime = Instant.parse("2022-01-01T00:00:00.00Z");
//
//        CreateMealRequest request = CreateMealRequest.builder()
//                .name("Chicken")
//                .weight(200.0)
//                .nutrition(new Nutrition(10.0, 20.0, 5.0, 150.0))
//                .storageId(null)
//                .consumedAt(fixedTime)
//                .build();
//
//        Meal registeredMeal = mealService.registerMeal(this.testUser.getId(), request);
//
//        assertThat(registeredMeal).isNotNull();
//        assertThat(registeredMeal.getId()).as("ID should be generated by UUID").isNotBlank();
//        assertThat(registeredMeal.getConsumedAt()).isEqualTo(fixedTime);
//        assertThat(registeredMeal.getUserId()).isEqualTo(this.testUser.getId());
//
//        Optional<Meal> mealFromDb = mealRepository.findById(registeredMeal.getId());
//
//        assertThat(mealFromDb.get().getUserId()).isEqualTo(this.testUser.getId());
//        assertThat(mealFromDb.get().getName()).isEqualTo("Chicken");
//        assertThat(mealFromDb.get().getWeight()).isEqualTo(200.0);
//        assertThat(mealFromDb.get().getNutrition()).isEqualTo(new Nutrition(10.0, 20.0, 5.0, 150.0));
//        assertThat(mealFromDb.get().getConsumedAt()).isEqualTo(fixedTime);
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//
//    @Test
//    @DisplayName("getMealsForUserBetween() should return all meals for specific user")
//    void getMealByUserIdRetrievesOnlyCorrectUserMeals() {
//        final Instant fixedTime1 = Instant.parse("2022-01-01T00:00:00.00Z");
//        final Instant fixedTime2 = Instant.parse("2022-01-01T15:00:00.00Z");
//
//        CreateMealRequest request1 = CreateMealRequest.builder()
//                .name("Pizza")
//                .weight(150.0)
//                .nutrition(new Nutrition(10.0, 20.0, 5.0, 150.0))
//                .consumedAt(fixedTime1)
//                .build();
//
//        CreateMealRequest request2 = CreateMealRequest.builder()
//                .name("Pizza")
//                .weight(150.0)
//                .nutrition(new Nutrition(10.0, 20.0, 5.0, 150.0))
//                .consumedAt(fixedTime2)
//                .build();
//
//        CreateMealRequest decoyRequest = CreateMealRequest.builder()
//                .name("Not Pizza")
//                .weight(150.0)
//                .nutrition(new Nutrition(10.0, 20.0, 5.0, 150.0))
//                .build();
//
//        Meal registeredMeal1 = mealService.registerMeal(this.testUser.getId(), request1);
//        Meal registeredMeal2 = mealService.registerMeal(this.testUser.getId(), request2);
//        Meal registeredDecoyMeal = mealService.registerMeal(this.otherTestUser.getId(), decoyRequest);
//
//        Collection<Meal> mealsFromDb = mealService.getMealsForUserBetween(this.testUser.getId(), fixedTime1, fixedTime2);
//
//        assertThat(mealsFromDb).hasSize(2);
//        assertThat(mealsFromDb)
//                .extracting(Meal::getId)
//                .containsExactlyInAnyOrder(registeredMeal1.getId(), registeredMeal2.getId());
//
//        assertThat(mealsFromDb)
//                .extracting(Meal::getUserId)
//                .doesNotContain(this.otherTestUser.getId());
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//
//    @Test
//    @DisplayName("getMealsForUserBetween() should return an empty array if no meals exist for time period")
//    void getMealByUserIdRetrievesEmptyArrayWhenNoMealsExist() {
//        final Instant fixedTime1 = Instant.parse("2022-02-01T00:00:00.00Z");
//        final Instant fixedTime2 = Instant.parse("2022-02-01T23:59:00.00Z");
//        final Instant consumedTime = Instant.parse("2022-01-01T11:00:00.00Z");
//
//        CreateMealRequest request = CreateMealRequest.builder()
//                .name("Pizza")
//                .weight(150.0)
//                .nutrition(new Nutrition(10.0, 20.0, 5.0, 150.0))
//                .consumedAt(consumedTime)
//                .build();
//
//        Meal registeredMeal = mealService.registerMeal(this.testUser.getId(), request);
//        Collection<Meal> allMealsFromDb = mealRepository.findAll();
//        assertThat(allMealsFromDb).as("Meals should be present in database").isNotEmpty();
//
//        Collection<Meal> mealsFromDb = mealService.getMealsForUserBetween(this.testUser.getId(), fixedTime1, fixedTime2);
//        assertThat(mealsFromDb).as("Meals for specific user should only be retrieved").isEmpty();
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//
//    @Test
//    @DisplayName("getDailyNutrition() should return summed nutrition values for the given day")
//    void getDailyNutritionReturnsSummedNutritionForDay() {
//        final Instant fixedTime1 = Instant.parse("2022-01-01T00:00:00.00Z");
//        final Instant fixedTime2 = Instant.parse("2022-01-01T23:59:59.00Z");
//        final Instant consumedTime1 = Instant.parse("2022-01-01T11:00:00.00Z");
//        final Instant consumedTime2 = Instant.parse("2022-01-01T13:00:00.00Z");
//
//        CreateMealRequest request1 = CreateMealRequest.builder()
//                .name("Pizza")
//                .weight(150.0)
//                .nutrition(new Nutrition(10.0, 20.0, 5.0, 150.0))
//                .consumedAt(consumedTime1)
//                .build();
//
//        CreateMealRequest request2 = CreateMealRequest.builder()
//                .name("Pizza")
//                .weight(150.0)
//                .nutrition(new Nutrition(10.0, 20.0, 5.0, 150.0))
//                .consumedAt(consumedTime2)
//                .build();
//
//        Meal registeredMeal1 = mealService.registerMeal(this.testUser.getId(), request1);
//        Meal registeredMeal2 = mealService.registerMeal(this.testUser.getId(), request2);
//
//        Nutrition dailyNutritionSum = mealService.getDailyNutrition(this.testUser.getId(), fixedTime1, fixedTime2);
//
//        assertThat(dailyNutritionSum)
//                .extracting(Nutrition::protein,
//                        Nutrition::carbs,
//                        Nutrition::fat,
//                        Nutrition::kcal
//                )
//                .containsExactly(20.0, 40.0, 10.0, 300.0);
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//
//    @Test
//    @DisplayName("getDailyNutrition() should return zero nutrition values for the given day if no meals exist")
//    void getDailyNutritionReturnsZeroNutritionForDayWhenNoMealsExist() {
//        final Instant fixedTime1 = Instant.parse("2022-02-01T00:00:00.00Z");
//        final Instant fixedTime2 = Instant.parse("2022-02-01T23:59:59.00Z");
//
//        Nutrition dailyNutritionSum = mealService.getDailyNutrition(this.testUser.getId(), fixedTime1, fixedTime2);
//
//        assertThat(dailyNutritionSum).isEqualTo(new Nutrition(0.0, 0.0, 0.0, 0.0));
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//
//    @Test
//    @DisplayName("deleteMeal() should remove meal from database")
//    void removeMealRemovesMealFromDatabase() {
//        final Instant fixedTime = Instant.parse("2022-01-01T00:00:00.00Z");
//
//        CreateMealRequest request = CreateMealRequest.builder()
//                .name("Pizza")
//                .weight(150.0)
//                .nutrition(new Nutrition(10.0, 20.0, 5.0, 150.0))
//                .consumedAt(fixedTime)
//                .build();
//
//        Meal registeredMeal = mealService.registerMeal(this.testUser.getId(), request);
//        assertThat(mealRepository.findById(registeredMeal.getId())).isPresent();
//
//        mealService.deleteMeal(this.testUser.getId(), registeredMeal.getId());
//        assertThat(mealRepository.findById(registeredMeal.getId())).isEmpty();
//    }
//}