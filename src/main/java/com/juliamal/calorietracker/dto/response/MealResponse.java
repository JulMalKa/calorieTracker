package com.juliamal.calorietracker.dto.response;

import com.juliamal.calorietracker.model.MealType;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class MealResponse {
    private Long id;
    private MealType mealType;
    private LocalDate date;
    private List<MealEntryResponse> entries;
    private Double totalCalories;
    private Double totalProtein;
    private Double totalCarbs;
    private Double totalFat;
}
