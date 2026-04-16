package com.juliamal.calorietracker.controller;

import com.juliamal.calorietracker.dto.request.MealEntryRequest;
import com.juliamal.calorietracker.dto.response.MealEntryResponse;
import com.juliamal.calorietracker.mappers.MealEntryMapper;
import com.juliamal.calorietracker.model.MealEntry;
import com.juliamal.calorietracker.service.MealEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meal/{mealId}/meal-entries")
@RequiredArgsConstructor
public class MealEntryController {

    private final MealEntryService mealEntryService;
    private final MealEntryMapper mealEntryMapper;

    @PostMapping
    public ResponseEntity<MealEntryResponse> addMealEntry(@PathVariable Long mealId, @Valid @RequestBody MealEntryRequest request) {
        var entity = mealEntryService.addMealEntry(request);
        return ResponseEntity.ok(mealEntryMapper.toDto(entity));
    }

    @GetMapping
    public List<MealEntryResponse> getEntriesForMeal(@PathVariable Long mealId) {
        var entries = mealEntryService.getEntriesForMeal(mealId);
        return mealEntryMapper.toDtoList(entries);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealEntry(@PathVariable Long mealId, @PathVariable Long id) {
        mealEntryService.deleteMealEntry(id);
        return ResponseEntity.noContent().build();
    }
}