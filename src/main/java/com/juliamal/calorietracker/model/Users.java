package com.juliamal.calorietracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private Integer age;
    private Double weight;
    private Double height;
    private Integer dailyCalorieGoal;

}
