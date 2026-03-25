package com.juliamal.calorietracker;

import com.juliamal.calorietracker.model.Product;
import com.juliamal.calorietracker.model.Users;
import com.juliamal.calorietracker.repository.ProductRepository;
import com.juliamal.calorietracker.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    @Override
    public void run(String... args) {
        productRepository.saveAll(List.of(
                createProduct("Kurczak pierś", 165.0, 31.0, 0.0, 3.6),
                createProduct("Ryż biały", 130.0, 2.7, 28.0, 0.3),
                createProduct("Jajko", 155.0, 13.0, 1.1, 11.0),
                createProduct("Banan", 89.0, 1.1, 23.0, 0.3),
                createProduct("Łosoś", 208.0, 20.0, 0.0, 13.0)
        ));
        usersRepository.saveAll(List.of(
                createUser("Julia", "julia@email.com", "haslo", 25, 60.0, 168.0, 2000)
        ));
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

    private Users createUser(String username, String email, String password, Integer age, Double weight, Double height, Integer dailyCalorieGoal) {
        Users u = new Users();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(password);
        u.setAge(age);
        u.setWeight(weight);
        u.setHeight(height);
        u.setDailyCalorieGoal(dailyCalorieGoal);
        return u;
    }
}

