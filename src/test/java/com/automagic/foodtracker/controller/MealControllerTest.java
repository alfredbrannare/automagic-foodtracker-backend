package com.automagic.foodtracker.controller;

import com.automagic.foodtracker.config.WithMockUserId;
import com.automagic.foodtracker.controller.meal.MealsController;
import com.automagic.foodtracker.dto.request.meal.CreateMealRequest;
import com.automagic.foodtracker.entity.Meal;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.service.meal.MealService;
import com.automagic.foodtracker.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MealsController.class,
excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
})

@AutoConfigureMockMvc(addFilters = false)
public class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private MealService mealService;

    private Meal mockedMealFromStorage;
    private Meal mockedMealManual;
    private final String mockUserId = "userId123";

    @BeforeEach
    void setUp() {
        mockedMealFromStorage = new Meal();
        mockedMealFromStorage.setId("meal123");
        mockedMealFromStorage.setName("Chicken");
        mockedMealFromStorage.setWeight(200.0);
        mockedMealFromStorage.setNutrition(new Nutrition(1.1, 1.1, 1.1, 1.1));
        mockedMealFromStorage.setStorageId("storage123");
        mockedMealFromStorage.setConsumedAt(Instant.now());

        mockedMealManual = new Meal();
        mockedMealManual.setId("meal456");
        mockedMealManual.setName("Pizza");
        mockedMealManual.setWeight(150.0);
        mockedMealManual.setNutrition(new Nutrition(10.0, 20.0, 5.0, 150.0));
        mockedMealManual.setConsumedAt(Instant.now());
    }

    @Test
    @WithMockUserId(mockUserId)
    void addMealReturnsCreatedMealFromStorage() throws Exception {
        CreateMealRequest request = CreateMealRequest.builder()
                .name(mockedMealFromStorage.getName())
                .weight(mockedMealFromStorage.getWeight())
                .nutrition(mockedMealFromStorage.getNutrition())
                .storageId(mockedMealFromStorage.getStorageId())
                .consumedAt(mockedMealFromStorage.getConsumedAt())
                .build();

        when(mealService.registerMeal(eq(mockUserId), any(CreateMealRequest.class)))
                .thenReturn(mockedMealFromStorage);

        mockMvc.perform(post("/api/meals")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("meal123"))
                .andExpect(jsonPath("$.name").value("Chicken"))
                .andExpect(jsonPath("$.storageId").value("storage123"));
    }

    @Test
    @WithMockUserId(mockUserId)
    void addMealReturnsCreatedMealManual() throws Exception {
        CreateMealRequest request = CreateMealRequest.builder()
                .name(mockedMealManual.getName())
                .weight(mockedMealManual.getWeight())
                .nutrition(mockedMealManual.getNutrition())
                .consumedAt(mockedMealManual.getConsumedAt())
                .build();

        when(mealService.registerMeal(eq(mockUserId), any(CreateMealRequest.class)))
                .thenReturn(mockedMealManual);

        mockMvc.perform(post("/api/meals")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("meal456"))
                .andExpect(jsonPath("$.name").value("Pizza"))
                .andExpect(jsonPath("$.storageId").value(nullValue()));
    }

    @Test
    @WithMockUserId(mockUserId)
    void addMealReturnsBadRequestOnNullName() throws Exception {
        CreateMealRequest invalidRequest = CreateMealRequest.builder()
                .name(null)
                .weight(200.0)
                .nutrition(new Nutrition(1.1, 1.1, 1.1, 1.1))
                .consumedAt(Instant.now())
                .build();

        mockMvc.perform(post("/api/meals")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(invalidRequest)))

                .andDo(print())

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").value("name"));
    }

    @Test
    @WithMockUserId(mockUserId)
    void getMealsReturnsMealsForSpecificDate() throws Exception {
        List<Meal> mockMeals = List.of(mockedMealManual);

        when(mealService.getMealsForUserBetween(eq(mockUserId), any(Instant.class), any(Instant.class)))
                .thenReturn(mockMeals);

        mockMvc.perform((get("/api/meals")
                .param("date", "2024-01-15")))

                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value("meal456"))
                .andExpect(jsonPath("$.[0].name").value("Pizza"))
                .andExpect(jsonPath("$.[0].storageId").value(nullValue()));

    }

    @Test
    @WithMockUserId(mockUserId)
    void getDailyNutritionSummaryReturnsCorrectTotals() throws Exception {
        Nutrition mockSummary = new Nutrition(50.0, 50.0, 50.0, 50.0);

        when(mealService.getDailyNutrition(eq(mockUserId), any(Instant.class), any(Instant.class)))
                .thenReturn(mockSummary);

        mockMvc.perform((get("/api/meals/summary")
                .param("date", "2024-01-15")))

                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.protein").value("50.0"))
                .andExpect(jsonPath("$.carbs").value("50.0"))
                .andExpect(jsonPath("$.fat").value("50.0"))
                .andExpect(jsonPath("$.kcal").value("50.0"));
    }

    @Test
    @WithMockUserId(mockUserId)
    void getDailyNutritionSummaryReturnsEmptyTotals() throws Exception {
        Nutrition mockSummary = new Nutrition(0.0, 0.0, 0.0, 0.0);

        when(mealService.getDailyNutrition(eq(mockUserId), any(Instant.class), any(Instant.class)))
                .thenReturn(mockSummary);

        mockMvc.perform((get("/api/meals/summary")
                        .param("date", "2024-01-15")))

                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.protein").value("0.0"))
                .andExpect(jsonPath("$.carbs").value("0.0"))
                .andExpect(jsonPath("$.fat").value("0.0"))
                .andExpect(jsonPath("$.kcal").value("0.0"));
    }

    @Test
    @WithMockUserId(mockUserId)
    void deleteMealReturnsConfirmationMessage() throws Exception {
        final String mealIdToDelete = "meal123";

        mockMvc.perform(delete("/api/meals/{mealId}", mealIdToDelete))

                .andDo(print())

                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
