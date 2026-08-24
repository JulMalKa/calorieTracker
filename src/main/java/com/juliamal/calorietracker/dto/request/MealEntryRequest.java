package com.juliamal.calorietracker.dto.request;

import jakarta.validation.constraints.*;


public record MealEntryRequest (

    @NotNull(message = "Meal ID is required")
    Long mealId,

    @NotNull(message = "Product ID is required")
    Long productId,

    @NotNull(message = "Grams value is required")
    @Positive(message = "Grams value must be over 0")
    Double amountInGrams
) {}
