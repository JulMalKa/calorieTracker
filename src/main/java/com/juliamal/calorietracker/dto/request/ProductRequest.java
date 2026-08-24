package com.juliamal.calorietracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(
        @NotBlank(message = "Product name cannot be blank")
        String name,

        @NotNull(message = "Calories are required")
        @PositiveOrZero(message = "Calories cannot be negative")
        Double caloriesPer100g,

        @NotNull(message = "Protein amount is required")
        @PositiveOrZero(message = "Protein cannot be negative")
        Double protein,

        @NotNull(message = "Carbs amount is required")
        @PositiveOrZero(message = "Carbs cannot be negative")
        Double carbs,

        @NotNull(message = "Fat amount is required")
        @PositiveOrZero(message = "Fat cannot be negative")
        Double fat
) {
}
