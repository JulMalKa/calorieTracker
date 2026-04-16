package com.juliamal.calorietracker.dto.response;

import lombok.Data;

@Data
public class MealEntryResponse {
    private Long id;
    private Long mealId;
    private Double amountInGrams;
    private String productName;
}