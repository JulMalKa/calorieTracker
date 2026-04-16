package com.juliamal.calorietracker.dto.request;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class MealEntryRequest {

    @NotNull(message = "Meal ID is required")
    private Long mealId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Grams value is required")
    @Positive(message = "Grams value must be over 0")
    private Double amountInGrams;
}
