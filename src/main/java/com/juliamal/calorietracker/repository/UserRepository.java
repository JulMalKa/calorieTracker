package com.juliamal.calorietracker.repository;

import com.juliamal.calorietracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
