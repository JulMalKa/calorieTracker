package com.juliamal.calorietracker.service;

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

    public Users addUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public Users getUserById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No such user found with id: " + id));
    }

    public Users updateUser(Long id, Users updatedUser) {
        Users existing = getUserById(id);
        existing.setUsername(updatedUser.getUsername());
        existing.setEmail(updatedUser.getEmail());
        existing.setAge(updatedUser.getAge());
        existing.setWeight(updatedUser.getWeight());
        existing.setHeight(updatedUser.getHeight());
        existing.setDailyCalorieGoal(updatedUser.getDailyCalorieGoal());
        existing.setDailyProteinGoal(updatedUser.getDailyProteinGoal());
        existing.setDailyCarbsGoal(updatedUser.getDailyCarbsGoal());
        existing.setDailyFatGoal(updatedUser.getDailyFatGoal());
        return usersRepository.save(existing);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

}
