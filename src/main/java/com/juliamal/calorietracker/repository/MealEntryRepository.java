package com.juliamal.calorietracker.repository;

import com.juliamal.calorietracker.model.MealEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealEntryRepository extends JpaRepository<MealEntry, Long> {
    List<MealEntry> findByMealId(Long mealId);
}
