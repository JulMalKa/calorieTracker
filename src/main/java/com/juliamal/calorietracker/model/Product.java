package com.juliamal.calorietracker.model;


import jakarta.persistence.*;
import lombok.Data;

@Data //adds getters, setters, equals ans toString
@Entity //says its table in h2
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double caloriesPer100g;
    private Double protein;
    private Double carbs;
    private Double fat;
}
