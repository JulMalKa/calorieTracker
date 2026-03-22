package com.juliamal.calorietracker.service;

import com.juliamal.calorietracker.model.Product;
import com.juliamal.calorietracker.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //automatycznie tworzy konstruktor - dependency injection
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produkt nie znaleziony"));
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing =  getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setCaloriesPer100g(updatedProduct.getCaloriesPer100g());
        existing.setProtein(updatedProduct.getProtein());
        existing.setCarbs(updatedProduct.getCarbs());
        existing.setFat(updatedProduct.getFat());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
