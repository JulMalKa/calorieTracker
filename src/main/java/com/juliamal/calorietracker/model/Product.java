package com.juliamal.calorietracker.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name of the product cannot be blank")
    private String name;

    @NotNull(message = "Calories are necessary")
    @Positive(message = "Calories must be over 0")
    private Double caloriesPer100g;

    @Min(value = 0, message = "Protein cannot be negative")
    private Double protein;

    @Min(value = 0, message = "Carbs cannot be negative")
    private Double carbs;

    @Min(value = 0, message = "Fat cannot be negative")
    private Double fat;
}

