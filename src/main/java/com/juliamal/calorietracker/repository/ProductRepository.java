package com.juliamal.calorietracker.repository;

import com.juliamal.calorietracker.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
//JpaRepository daje metody tj findAll, save, delete
