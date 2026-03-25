package com.juliamal.calorietracker.repository;

import com.juliamal.calorietracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
