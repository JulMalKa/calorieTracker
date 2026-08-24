package com.juliamal.calorietracker.dto.request;

import jakarta.validation.constraints.*;

public record UserRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Must be a valid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,

        @NotNull(message = "Age is required")
        @Positive(message = "Age must be greater than 0")
        Integer age,

        @NotNull(message = "Weight is required")
        @Positive(message = "Weight must be greater than 0")
        Double weight,

        @NotNull(message = "Height is required")
        @Positive(message = "Height must be greater than 0")
        Double height,

        @NotNull(message = "Calorie goal is required")
        @Positive(message = "Goal must be greater than 0")
        Integer dailyCalorieGoal,

        @NotNull(message = "Protein goal is required")
        @PositiveOrZero(message = "Goal cannot be negative")
        Double dailyProteinGoal,

        @NotNull(message = "Carbs goal is required")
        @PositiveOrZero(message = "Goal cannot be negative")
        Double dailyCarbsGoal,

        @NotNull(message = "Fat goal is required")
        @PositiveOrZero(message = "Goal cannot be negative")
        Double dailyFatGoal
) {
}
