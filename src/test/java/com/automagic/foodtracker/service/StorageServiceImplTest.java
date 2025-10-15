package com.automagic.foodtracker.service;

import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.entity.Storage;
import com.automagic.foodtracker.repository.storage.StorageRepository;
import com.automagic.foodtracker.service.storage.StorageService;
import com.automagic.foodtracker.service.storage.StorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@DataJpaTest
public class StorageServiceImplTest {
    
    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("foodtracker")
            .withUsername("user")
            .withPassword("password");
    
    @Autowired
    private StorageRepository storageRepository;
    private StorageService storageService;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        storageRepository.deleteAll();
        storageService = new StorageServiceImpl(storageRepository);
    }

    @Test
    void testGetNutritionReturnsCorrectValue() {
        Storage storage = new Storage();

        Nutrition nutrition = new Nutrition(10, 20, 5, 150);
        storage.setUserId("user123");
        storage.setNutritionPer100g(nutrition);

        storageRepository.save(storage);

        Nutrition result = storageService.getNutrition(storage.getId(), "user123");
        assertThat(result.protein()).isEqualTo(10);
        assertThat(result.carbs()).isEqualTo(20);
        assertThat(result.fat()).isEqualTo(5);
        assertThat(result.kcal()).isEqualTo(150);
    }

}
