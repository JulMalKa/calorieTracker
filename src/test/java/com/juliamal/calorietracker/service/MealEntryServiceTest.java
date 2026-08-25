package com.juliamal.calorietracker.service;

import com.juliamal.calorietracker.dto.request.MealEntryRequest;
import com.juliamal.calorietracker.exception.ResourceNotFoundException;
import com.juliamal.calorietracker.model.Meal;
import com.juliamal.calorietracker.model.MealEntry;
import com.juliamal.calorietracker.model.Product;
import com.juliamal.calorietracker.repository.MealEntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealEntryServiceTest {

    @Mock
    private MealEntryRepository mealEntryRepository;

    @Mock
    private MealService mealService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private MealEntryService mealEntryService;

    @Test
    void addMealEntry_whenMealAndProductExist_savesEntry() {
        MealEntryRequest request = new MealEntryRequest(1L, 2L, 200.0);

        Meal meal = new Meal();
        meal.setId(1L);
        Product product = new Product();
        product.setId(2L);
        product.setName("Rice");

        when(mealService.getMealById(1L)).thenReturn(meal);
        when(productService.getProductById(2L)).thenReturn(product);
        when(mealEntryRepository.save(any(MealEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MealEntry result = mealEntryService.addMealEntry(request);

        assertThat(result.getMeal()).isEqualTo(meal);
        assertThat(result.getProduct()).isEqualTo(product);
        assertThat(result.getAmountInGrams()).isEqualTo(200.0);
    }

    @Test
    void addMealEntry_whenMealDoesNotExist_propagatesResourceNotFoundException() {
        MealEntryRequest request = new MealEntryRequest(99L, 2L, 200.0);

        when(mealService.getMealById(99L)).thenThrow(new ResourceNotFoundException("Meal", 99L));

        assertThatThrownBy(() -> mealEntryService.addMealEntry(request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(mealEntryRepository, never()).save(any());
    }

    @Test
    void getEntriesForMeal_returnsEntriesFromRepository() {
        MealEntry entry = new MealEntry();
        entry.setId(10L);
        when(mealEntryRepository.findByMealId(1L)).thenReturn(List.of(entry));

        List<MealEntry> result = mealEntryService.getEntriesForMeal(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(10L);
    }

    @Test
    void deleteMealEntry_whenNotFound_throwsAndNeverDeletes() {
        when(mealEntryRepository.existsById(404L)).thenReturn(false);

        assertThatThrownBy(() -> mealEntryService.deleteMealEntry(404L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(mealEntryRepository, never()).deleteById(any());
    }

    @Test
    void deleteMealEntry_whenExists_deletesIt() {
        when(mealEntryRepository.existsById(1L)).thenReturn(true);

        mealEntryService.deleteMealEntry(1L);

        verify(mealEntryRepository, times(1)).deleteById(1L);
    }
}