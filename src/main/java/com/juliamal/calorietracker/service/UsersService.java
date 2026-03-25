package com.juliamal.calorietracker.service;

import com.juliamal.calorietracker.model.Users;
import com.juliamal.calorietracker.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Users addUser(Users users) {
        return usersRepository.save(users);
    }

    public Users getUserById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie znaleziony"));
    }

    public Users updateUser(Long id, Users updatedUser) {
        Users existing = getUserById(id);
        existing.setUsername(updatedUser.getUsername());
        existing.setEmail(updatedUser.getEmail());
        existing.setAge(updatedUser.getAge());
        existing.setWeight(updatedUser.getWeight());
        existing.setHeight(updatedUser.getHeight());
        existing.setDailyCalorieGoal(updatedUser.getDailyCalorieGoal());
        return usersRepository.save(existing);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

}
