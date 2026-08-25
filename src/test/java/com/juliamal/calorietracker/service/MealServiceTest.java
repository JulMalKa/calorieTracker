package com.juliamal.calorietracker.service;

import com.juliamal.calorietracker.dto.request.MealRequest;
import com.juliamal.calorietracker.dto.response.MealResponse;
import com.juliamal.calorietracker.exception.DuplicateResourceException;
import com.juliamal.calorietracker.exception.ResourceNotFoundException;
import com.juliamal.calorietracker.mappers.MealMapper;
import com.juliamal.calorietracker.model.*;
import com.juliamal.calorietracker.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UsersService usersService;

    @Mock
    private MealMapper mealMapper;

    @InjectMocks
    private MealService mealService;

    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1L);
        user.setUsername("julia");
        user.setDailyCalorieGoal(2000);
    }

    @Test
    void createMeal_whenMealTypeAlreadyExistsForDay_throwsDuplicateResourceException() {
        MealRequest request = new MealRequest(1L, MealType.BREAKFAST, LocalDate.of(2026, 1, 15));
        Meal alreadyExisting = new Meal();

        when(mealRepository.findByUserIdAndDateAndMealType(1L, request.date(), MealType.BREAKFAST))
                .thenReturn(Optional.of(alreadyExisting));

        assertThatThrownBy(() -> mealService.createMeal(request))
                .isInstanceOf(DuplicateResourceException.class);

        verify(mealRepository, never()).save(any());
    }

    @Test
    void createMeal_whenNoDuplicate_savesNewMeal() {
        MealRequest request = new MealRequest(1L, MealType.LUNCH, LocalDate.of(2026, 1, 15));

        when(mealRepository.findByUserIdAndDateAndMealType(1L, request.date(), MealType.LUNCH))
                .thenReturn(Optional.empty());
        when(usersService.getUserById(1L)).thenReturn(user);
        when(mealRepository.save(any(Meal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Meal result = mealService.createMeal(request);

        assertThat(result.getMealType()).isEqualTo(MealType.LUNCH);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getDate()).isEqualTo(LocalDate.of(2026, 1, 15));
    }

    @Test
    void getMealById_whenNotFound_throwsResourceNotFoundException() {
        when(mealRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mealService.getMealById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getMealWithCalories_withMultipleEntries_calculatesCorrectMacroTotals() {
        Meal meal = new Meal();
        meal.setId(5L);
        meal.setMealType(MealType.BREAKFAST);

        Product banana = new Product();
        banana.setCaloriesPer100g(89.0);
        banana.setProtein(1.1);
        banana.setCarbs(23.0);
        banana.setFat(0.3);

        MealEntry bananaEntry = new MealEntry();
        bananaEntry.setProduct(banana);
        bananaEntry.setAmountInGrams(150.0);

        Product egg = new Product();
        egg.setCaloriesPer100g(155.0);
        egg.setProtein(13.0);
        egg.setCarbs(1.1);
        egg.setFat(11.0);

        MealEntry eggEntry = new MealEntry();
        eggEntry.setProduct(egg);
        eggEntry.setAmountInGrams(50.0);

        meal.setEntries(List.of(bananaEntry, eggEntry));

        when(mealRepository.findById(5L)).thenReturn(Optional.of(meal));
        when(mealMapper.toDto(meal)).thenReturn(new MealResponse());

        MealResponse result = mealService.getMealWithCalories(5L);

        assertThat(result.getTotalCalories()).isCloseTo(211.0, within(0.001));
        assertThat(result.getTotalProtein()).isCloseTo(8.15, within(0.001));
        assertThat(result.getTotalCarbs()).isCloseTo(35.05, within(0.001));
        assertThat(result.getTotalFat()).isCloseTo(5.95, within(0.001));
    }

    @Test
    void getMealWithCalories_withNoEntries_returnsZeroForAllMacros() {
        Meal meal = new Meal();
        meal.setId(6L);
        meal.setEntries(List.of());

        when(mealRepository.findById(6L)).thenReturn(Optional.of(meal));
        when(mealMapper.toDto(meal)).thenReturn(new MealResponse());

        MealResponse result = mealService.getMealWithCalories(6L);

        assertThat(result.getTotalCalories()).isEqualTo(0.0);
        assertThat(result.getTotalProtein()).isEqualTo(0.0);
        assertThat(result.getTotalCarbs()).isEqualTo(0.0);
        assertThat(result.getTotalFat()).isEqualTo(0.0);
    }


    @Test
    void deleteMeal_whenNotFound_throwsAndNeverDeletes() {
        when(mealRepository.existsById(404L)).thenReturn(false);

        assertThatThrownBy(() -> mealService.deleteMeal(404L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(mealRepository, never()).deleteById(any());
    }
}
