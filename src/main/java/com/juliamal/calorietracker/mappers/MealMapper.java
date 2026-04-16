package com.juliamal.calorietracker.mappers;

import com.juliamal.calorietracker.dto.response.MealResponse;
import com.juliamal.calorietracker.model.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MealEntryMapper.class})
public interface MealMapper {
    @Mapping(source = "entries", target = "entries")
    @Mapping(target = "totalCalories", ignore = true)
    MealResponse toDto(Meal meal);

    List<MealResponse> toDtoList(List<Meal> meals);
}
