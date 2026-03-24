package com.juliamal.calorietracker.service;

import com.juliamal.calorietracker.model.User;
import com.juliamal.calorietracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie znaleziony"));
    }

    public User updateUser(Long id, User updatedUser) {
        User existing = getUserById(id);
        existing.setUsername(updatedUser.getUsername());
        existing.setEmail(updatedUser.getEmail());
        existing.setAge(updatedUser.getAge());
        existing.setWeight(updatedUser.getWeight());
        existing.setHeight(updatedUser.getHeight());
        existing.setDailyCalorieGoal(updatedUser.getDailyCalorieGoal());
        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
