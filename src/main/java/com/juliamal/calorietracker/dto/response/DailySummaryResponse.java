package com.juliamal.calorietracker.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class DailySummaryResponse {
    private LocalDate date;
    private List<MealResponse> meals;

    private Double totalCalories;
    private Double totalProtein;
    private Double totalCarbs;
    private Double totalFat;

    private Integer calorieGoal;
    private Double proteinGoal;
    private Double carbsGoal;
    private Double fatGoal;

    private Double remainingCalories;
    private Double remainingProtein;
    private Double remainingCarbs;
    private Double remainingFat;
}
