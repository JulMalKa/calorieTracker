package com.juliamal.calorietracker.service;

import com.juliamal.calorietracker.dto.request.UserRequest;
import com.juliamal.calorietracker.exception.ResourceNotFoundException;
import com.juliamal.calorietracker.model.Users;
import com.juliamal.calorietracker.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Users addUser(UserRequest request) {
        Users user = new Users();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setAge(request.age());
        user.setWeight(request.weight());
        user.setHeight(request.height());
        user.setDailyCalorieGoal(request.dailyCalorieGoal());
        user.setDailyProteinGoal(request.dailyProteinGoal());
        user.setDailyCarbsGoal(request.dailyCarbsGoal());
        user.setDailyFatGoal(request.dailyFatGoal());
        return usersRepository.save(user);
    }

    public Users getUserById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    public Users updateUser(Long id, UserRequest request) {
        Users existing = getUserById(id);
        existing.setUsername(request.username());
        existing.setEmail(request.email());
        if (request.password() != null && !request.password().isBlank()) {
            existing.setPassword(passwordEncoder.encode(request.password()));
        }
        existing.setAge(request.age());
        existing.setWeight(request.weight());
        existing.setHeight(request.height());
        existing.setDailyCalorieGoal(request.dailyCalorieGoal());
        existing.setDailyProteinGoal(request.dailyProteinGoal());
        existing.setDailyCarbsGoal(request.dailyCarbsGoal());
        existing.setDailyFatGoal(request.dailyFatGoal());
        return usersRepository.save(existing);
    }

    public void deleteUser(Long id) {
        if (!usersRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", id);
        }
        usersRepository.deleteById(id);
    }

}
