package com.automagic.foodtracker.controller;

import com.automagic.foodtracker.config.WithMockUserId;
import com.automagic.foodtracker.controller.storage.StorageController;
import com.automagic.foodtracker.dto.request.storage.CreateStorageRequest;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.entity.Storage;
import com.automagic.foodtracker.filter.JwtAuthenticationFilter;
import com.automagic.foodtracker.service.storage.StorageService;
import com.automagic.foodtracker.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StorageController.class,
        excludeAutoConfiguration = {
                DataSourceAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class,
                JpaRepositoriesAutoConfiguration.class,
        })
@AutoConfigureMockMvc(addFilters = false)
public class StorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private StorageService storageService;

    private Storage mockedStorage;

    @BeforeEach
    void setUp() {
        mockedStorage = new Storage();
        mockedStorage.setId("123456789");
        mockedStorage.setName("Chicken");
        mockedStorage.setTotalWeight(200.0);
        mockedStorage.setConsumedWeight(0.0);
        mockedStorage.setWeightPerMeal(10.0);
        mockedStorage.setLowStockThreshold(10.0);
        mockedStorage.setNutritionPer100g(new Nutrition(1.1, 1.1, 1.1, 1.1));
        mockedStorage.setCreatedAt(Instant.now());

        when(storageService.registerStorage(eq("userId123"), any(CreateStorageRequest.class)))
                .thenReturn(mockedStorage);
    }

    @Test
    @WithMockUserId("userId123")
    void addStorageReturnsCreatedStorage() throws Exception {
        CreateStorageRequest request = CreateStorageRequest.builder()
                .name("Chicken")
                .totalWeight(200.0)
                .weightPerMeal(10.0)
                .lowStockThreshold(10.0)
                .nutritionPer100g(new Nutrition(1.1, 1.1, 1.1, 1.1))
                .build();

        mockMvc.perform(post("/api/storage")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123456789"))
                .andExpect(jsonPath("$.name").value("Chicken"))
                .andExpect(jsonPath("$.totalWeight").value(200.0))
                .andExpect(jsonPath("$.consumedWeight").value(0.0))
                .andExpect(jsonPath("$.weightPerMeal").value(10.0))
                .andExpect(jsonPath("$.lowStockThreshold").value(10.0))
                .andExpect(jsonPath("$.lowStock").value(false));
    }

    @Test
    @WithMockUserId("userId123")
    void addStorageReturnsBadRequestOnNullName() throws Exception {
        CreateStorageRequest invalidRequest = CreateStorageRequest.builder()
                .name(null)
                .totalWeight(200.0)
                .weightPerMeal(10.0)
                .lowStockThreshold(10.0)
                .nutritionPer100g(new Nutrition(1.1, 1.1, 1.1, 1.1))
                .build();

        mockMvc.perform(post("/api/storage")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequest)))

                .andDo(print())

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").value("name"));
    }
}