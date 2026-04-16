package com.juliamal.calorietracker.dto.request;

import com.juliamal.calorietracker.model.MealType;
import lombok.Data;
import java.time.LocalDate;
import jakarta.validation.constraints.*;

@Data
public class MealRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Meal type is required")
    private MealType mealType;

    @NotNull(message = "Date is required")
    private LocalDate date;
}
