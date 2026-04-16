package com.juliamal.calorietracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Email(message = "Wrong email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;


    private String password;

    @Min(value = 1, message = "Age must be over 0")
    @Max(value = 150, message = "Provide valid age")
    private Integer age;

    @Positive(message = "Weight must be over 0")
    private Double weight;

    @Positive(message = "Height must be over 0")
    private Double height;

    @Min(value = 0, message = "Calorie goal cannot be negative")
    private Integer dailyCalorieGoal;

    @Min(value = 0, message = "Protein goal cannot be negative")
    private Double dailyProteinGoal;

    @Min(value = 0, message = "Carbs goal cannot be negative")
    private Double dailyCarbsGoal;

    @Min(value = 0, message = "Fat goal cannot be negative")
    private Double dailyFatGoal;

}
