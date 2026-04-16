package com.juliamal.calorietracker.service;

import com.juliamal.calorietracker.dto.request.MealEntryRequest;
import com.juliamal.calorietracker.model.MealEntry;
import com.juliamal.calorietracker.repository.MealEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealEntryService {

    private final MealEntryRepository mealEntryRepository;
    private final MealService mealService;
    private final ProductService productService;

    public MealEntry addMealEntry(MealEntryRequest request) {
        MealEntry entry = new MealEntry();
        entry.setMeal(mealService.getMealById(request.getMealId()));
        entry.setProduct(productService.getProductById(request.getProductId()));
        entry.setAmountInGrams(request.getAmountInGrams());
        return mealEntryRepository.save(entry);
    }

    public List<MealEntry> getEntriesForMeal(Long mealId) {
        return mealEntryRepository.findByMealId(mealId);
    }

    public void deleteMealEntry(Long id) {
        mealEntryRepository.deleteById(id);
    }
}