package com.juliamal.calorietracker.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "meal_entry")
public class MealEntry {   //pozycja w posilku
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double amountInGrams;
}
