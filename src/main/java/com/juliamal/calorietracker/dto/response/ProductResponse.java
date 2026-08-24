package com.juliamal.calorietracker.dto.response;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private Double caloriesPer100g;
    private Double protein;
    private Double carbs;
    private Double fat;
}
