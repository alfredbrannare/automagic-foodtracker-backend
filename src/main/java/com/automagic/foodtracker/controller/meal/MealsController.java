package com.automagic.foodtracker.controller.meal;

import com.automagic.foodtracker.dto.request.meal.CreateMealRequest;
import com.automagic.foodtracker.dto.response.meal.MealResponse;
import com.automagic.foodtracker.entity.Meal;
import com.automagic.foodtracker.entity.Nutrition;
import com.automagic.foodtracker.mapper.meal.MealMapper;
import com.automagic.foodtracker.security.AuthenticatedUser;
import com.automagic.foodtracker.service.meal.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealsController {
    private final MealService mealService;

    @PostMapping
    public ResponseEntity<MealResponse> addMeal(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody CreateMealRequest request) {

        Meal saved = mealService.registerMeal(user.getUserId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                MealResponse.builder()
                        .id(saved.getId())
                        .name(saved.getName())
                        .weight(saved.getWeight())
                        .consumedAt(saved.getConsumedAt())
                        .nutrition(saved.getNutrition())
                        .storageId(saved.getStorageId())
                        .build()
        );
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable String mealId) {

        mealService.deleteMeal(user.getUserId(), mealId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<MealResponse>> getMeals(
            @AuthenticationPrincipal AuthenticatedUser user,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        Instant from = targetDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant to = targetDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).minusNanos(1).toInstant();

        List<MealResponse> response = mealService.getMealsForUserBetween(user.getUserId(), from, to)
                .stream()
                .map(MealMapper::toResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/summary")
    public ResponseEntity<Nutrition> getDailyNutritionSummary(
            @AuthenticationPrincipal AuthenticatedUser user,
            @RequestParam(value = "default", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        Instant from = targetDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant to = targetDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).minusNanos(1).toInstant();

        Nutrition dailyNutrition = mealService.getDailyNutrition(user.getUserId(), from, to);

        return ResponseEntity.status(HttpStatus.OK).body(dailyNutrition);
    }

}
