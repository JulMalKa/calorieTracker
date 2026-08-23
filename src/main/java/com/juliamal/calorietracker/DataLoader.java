/*
package com.juliamal.calorietracker;

import com.juliamal.calorietracker.model.Meal;
import com.juliamal.calorietracker.model.MealEntry;
import com.juliamal.calorietracker.model.MealType;
import com.juliamal.calorietracker.model.Product;
import com.juliamal.calorietracker.model.Users;
import com.juliamal.calorietracker.repository.MealEntryRepository;
import com.juliamal.calorietracker.repository.MealRepository;
import com.juliamal.calorietracker.repository.ProductRepository;
import com.juliamal.calorietracker.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final MealRepository mealRepository;
    private final MealEntryRepository mealEntryRepository;

    @Override
    public void run(String... args) {
        Product chicken = createProduct("Chicken breast (raw)", 110.0, 21.5, 0.0, 1.3);
        Product rice = createProduct("White rice (dry)", 344.0, 6.7, 78.9, 0.7);
        Product egg = createProduct("Egg", 143.0, 12.6, 0.7, 9.5);
        Product banana = createProduct("Banana", 89.0, 1.1, 22.8, 0.3);
        Product salmon = createProduct("Salmon (raw)", 208.0, 20.0, 0.0, 13.0);
        Product oats = createProduct("Oatmeal", 379.0, 11.9, 67.7, 6.9);
        Product milk = createProduct("Milk 2%", 50.0, 3.3, 4.8, 2.0);
        Product apple = createProduct("Apple", 52.0, 0.3, 13.8, 0.2);
        Product oliveOil = createProduct("Olive oil", 884.0, 0.0, 0.0, 100.0);
        Product pasta = createProduct("Whole wheat pasta (dry)", 348.0, 14.5, 65.0, 2.5);
        Product beef = createProduct("Lean ground beef", 215.0, 21.0, 0.0, 15.0);

        productRepository.saveAll(List.of(
                chicken, rice, egg, banana, salmon, oats, milk, apple, oliveOil, pasta, beef
        ));

        Users julia = createUser("Julia", "julia@email.com", "password123", 25, 60.0, 168.0, 2000, 100.0, 175.0, 100.0);
        usersRepository.save(julia);


        Meal breakfast = createMeal(julia, LocalDate.parse("2026-03-25"), MealType.BREAKFAST);
        Meal lunch = createMeal(julia, LocalDate.parse("2026-03-25"), MealType.LUNCH);
        Meal dinner = createMeal(julia, LocalDate.parse("2026-03-25"), MealType.DINNER);
        Meal snack = createMeal(julia, LocalDate.parse("2026-03-25"), MealType.SNACKS);

        mealRepository.saveAll(List.of(breakfast, lunch, dinner, snack));


        MealEntry b1 = createMealEntry(breakfast, oats, 60.0);
        MealEntry b2 = createMealEntry(breakfast, milk, 200.0);
        MealEntry b3 = createMealEntry(breakfast, banana, 120.0);

        MealEntry l1 = createMealEntry(lunch, chicken, 150.0);
        MealEntry l2 = createMealEntry(lunch, rice, 80.0);
        MealEntry l3 = createMealEntry(lunch, oliveOil, 10.0);

        MealEntry d1 = createMealEntry(dinner, pasta, 70.0);
        MealEntry d2 = createMealEntry(dinner, beef, 120.0);

        MealEntry s1 = createMealEntry(snack, apple, 150.0);

        mealEntryRepository.saveAll(List.of(b1, b2, b3, l1, l2, l3, d1, d2, s1));
    }

    private Product createProduct(String name, Double calories, Double protein, Double carbs, Double fat) {
        Product p = new Product();
        p.setName(name);
        p.setCaloriesPer100g(calories);
        p.setProtein(protein);
        p.setCarbs(carbs);
        p.setFat(fat);
        return p;
    }

    private Users createUser(String username, String email, String password, Integer age, Double weight, Double height, Integer dailyCalorieGoal, Double protein, Double carbs, Double fat) {
        Users u = new Users();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(password);
        u.setAge(age);
        u.setWeight(weight);
        u.setHeight(height);
        u.setDailyCalorieGoal(dailyCalorieGoal);
        u.setDailyProteinGoal(protein);
        u.setDailyCarbsGoal(carbs);
        u.setDailyFatGoal(fat);
        return u;
    }

    private Meal createMeal(Users user, LocalDate date, MealType mealType) {
        Meal m = new Meal();
        m.setUser(user);
        m.setDate(date);
        m.setMealType(mealType);
        return m;
    }

    private MealEntry createMealEntry(Meal meal, Product product, Double weight) {
        MealEntry entry = new MealEntry();
        entry.setMeal(meal);
        entry.setProduct(product);
        entry.setAmountInGrams(weight);
        return entry;
    }
}

 */