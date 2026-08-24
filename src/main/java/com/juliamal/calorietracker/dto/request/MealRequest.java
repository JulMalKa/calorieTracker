package com.juliamal.calorietracker.dto.request;

import com.juliamal.calorietracker.model.MealType;
import java.time.LocalDate;
import jakarta.validation.constraints.*;


public record MealRequest (

    @NotNull(message = "User ID is required")
    Long userId,

    @NotNull(message = "Meal type is required")
    MealType mealType,

    @NotNull(message = "Date is required")
    LocalDate date
) {}
