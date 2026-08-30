package com.juliamal.calorietracker.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Integer age;
    private Double weight;
    private Double height;
    private Integer dailyCalorieGoal;
    private Double dailyProteinGoal;
    private Double dailyCarbsGoal;
    private Double dailyFatGoal;
}
