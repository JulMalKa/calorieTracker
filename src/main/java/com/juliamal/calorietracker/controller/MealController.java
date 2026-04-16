package com.juliamal.calorietracker.controller;

import com.juliamal.calorietracker.dto.request.MealRequest;
import com.juliamal.calorietracker.dto.response.DailySummaryResponse;
import com.juliamal.calorietracker.dto.response.MealResponse;
import com.juliamal.calorietracker.model.Meal;
import com.juliamal.calorietracker.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @PostMapping
    public ResponseEntity<Meal> createMeal(@Valid @RequestBody MealRequest request) {
        return ResponseEntity.ok(mealService.createMeal(request));
    }

    @GetMapping("/user/{userId}")
    public List<Meal> getMealsForUser(@PathVariable Long userId) {
        return mealService.getMealsForUser(userId);
    }

    @GetMapping("/{mealId}/calories")
    public ResponseEntity<MealResponse> getMealWithCalories(@PathVariable Long mealId) {
        return ResponseEntity.ok(mealService.getMealWithCalories(mealId));
    }

    // główny endpoint — pełne podsumowanie dnia
    @GetMapping("/user/{userId}/date/{date}/summary")
    public ResponseEntity<DailySummaryResponse> getDailySummary(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(mealService.getDailySummary(userId, date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}
