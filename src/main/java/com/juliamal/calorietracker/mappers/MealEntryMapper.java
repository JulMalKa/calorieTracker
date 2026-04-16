package com.juliamal.calorietracker.mappers;

import com.juliamal.calorietracker.dto.response.MealEntryResponse;
import com.juliamal.calorietracker.model.MealEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MealEntryMapper {
    @Mapping(source = "meal.id", target = "mealId")
    @Mapping(source = "product.name", target = "productName")
    MealEntryResponse toDto(MealEntry entry);
    List<MealEntryResponse> toDtoList(List<MealEntry> entries);
}