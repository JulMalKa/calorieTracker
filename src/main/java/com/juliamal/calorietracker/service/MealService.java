package com.juliamal.calorietracker.service;

import com.juliamal.calorietracker.dto.request.MealRequest;
import com.juliamal.calorietracker.dto.response.DailySummaryResponse;
import com.juliamal.calorietracker.dto.response.MacroSummaryResponse;
import com.juliamal.calorietracker.dto.response.MealResponse;
import com.juliamal.calorietracker.mappers.MealMapper;
import com.juliamal.calorietracker.model.Meal;
import com.juliamal.calorietracker.model.Users;
import com.juliamal.calorietracker.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final UsersService usersService;
    private final MealMapper mealMapper;

    public Meal createMeal(MealRequest request) {
        mealRepository.findByUserIdAndDateAndMealType(
                        request.userId(),
                        request.date(),
                        request.mealType())
                .ifPresent(m -> {
                    throw new IllegalStateException("This meal type already exists for this day");
                });

        Meal meal = new Meal();
        meal.setUser(usersService.getUserById(request.userId()));
        meal.setMealType(request.mealType());
        meal.setDate(request.date());
        return mealRepository.save(meal);
    }

    public Meal getMealById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("This meal doesnt exist"));
    }

    public List<MealResponse> getMealsForUser(Long userId) {
        List<Meal> meals = mealRepository.findByUserId(userId);
        return meals.stream()
                .map(meal -> {
                    MealResponse response = mealMapper.toDto(meal);
                    MacroSummaryResponse macros = calculateMacrosForMeal(meal);
                    response.setTotalCalories(macros.getTotalCalories());
                    response.setTotalProtein(macros.getTotalProtein());
                    response.setTotalCarbs(macros.getTotalCarbs());
                    response.setTotalFat(macros.getTotalFat());
                    return response;
                })
                .toList();
    }

    public List<Meal> getMealsForUserAndDate(Long userId, LocalDate date) {
        return mealRepository.findByUserIdAndDate(userId, date);
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }

    private MacroSummaryResponse calculateMacrosForMeal(Meal meal) {
        if (meal.getEntries() == null || meal.getEntries().isEmpty()) {
            return new MacroSummaryResponse(0.0, 0.0, 0.0, 0.0);
        }
        double calories = 0.0, protein = 0.0, carbs = 0.0, fat = 0.0;
        for (var entry : meal.getEntries()) {
            double ratio = entry.getAmountInGrams() / 100.0;
            calories += entry.getProduct().getCaloriesPer100g() * ratio;
            protein  += entry.getProduct().getProtein()         * ratio;
            carbs    += entry.getProduct().getCarbs()           * ratio;
            fat      += entry.getProduct().getFat()             * ratio;
        }
        return new MacroSummaryResponse(calories, protein, carbs, fat);
    }

    public MealResponse getMealWithCalories(Long mealId) {
        Meal meal = getMealById(mealId);
        MealResponse response = mealMapper.toDto(meal);
        MacroSummaryResponse macros = calculateMacrosForMeal(meal);
        response.setTotalCalories(macros.getTotalCalories());
        response.setTotalProtein(macros.getTotalProtein());
        response.setTotalCarbs(macros.getTotalCarbs());
        response.setTotalFat(macros.getTotalFat());
        return response;
    }



    public DailySummaryResponse getDailySummary(Long userId, LocalDate date) {
        List<Meal> meals = mealRepository.findByUserIdAndDate(userId, date);
        Users user = usersService.getUserById(userId);

        List<MealResponse> mealResponses = meals.stream()
                .map(meal -> {
                    MealResponse response = mealMapper.toDto(meal);
                    MacroSummaryResponse macros = calculateMacrosForMeal(meal);
                    response.setTotalCalories(macros.getTotalCalories());
                    response.setTotalProtein(macros.getTotalProtein());
                    response.setTotalCarbs(macros.getTotalCarbs());
                    response.setTotalFat(macros.getTotalFat());
                    return response;
                })
                .toList();

        double totalCalories = mealResponses.stream().mapToDouble(MealResponse::getTotalCalories).sum();
        double totalProtein = mealResponses.stream().mapToDouble(MealResponse::getTotalProtein).sum();
        double totalCarbs = mealResponses.stream().mapToDouble(MealResponse::getTotalCarbs).sum();
        double totalFat = mealResponses.stream().mapToDouble(MealResponse::getTotalFat).sum();

        DailySummaryResponse summary = new DailySummaryResponse();
        summary.setDate(date);
        summary.setMeals(mealResponses);

        summary.setTotalCalories(totalCalories);
        summary.setTotalProtein(totalProtein);
        summary.setTotalCarbs(totalCarbs);
        summary.setTotalFat(totalFat);

        summary.setCalorieGoal(user.getDailyCalorieGoal());
        summary.setProteinGoal(user.getDailyProteinGoal());
        summary.setCarbsGoal(user.getDailyCarbsGoal());
        summary.setFatGoal(user.getDailyFatGoal());

        summary.setRemainingCalories(user.getDailyCalorieGoal() != null
                ? user.getDailyCalorieGoal() - totalCalories : null);
        summary.setRemainingProtein(user.getDailyProteinGoal() != null
                ? user.getDailyProteinGoal() - totalProtein : null);
        summary.setRemainingCarbs(user.getDailyCarbsGoal() != null
                ? user.getDailyCarbsGoal() - totalCarbs : null);
        summary.setRemainingFat(user.getDailyFatGoal() != null
                ? user.getDailyFatGoal() - totalFat : null);

        return summary;
    }


}
