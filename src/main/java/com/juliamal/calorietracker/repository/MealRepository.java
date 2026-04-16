package com.juliamal.calorietracker.repository;

import com.juliamal.calorietracker.model.Meal;
import com.juliamal.calorietracker.model.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserId(Long userId);
    List<Meal> findByUserIdAndDate(Long userId, LocalDate date);
    Optional<Meal> findByUserIdAndDateAndMealType(Long userId, LocalDate date, MealType mealType);
}
