package com.juliamal.calorietracker.service;

import com.juliamal.calorietracker.dto.request.ProductRequest;
import com.juliamal.calorietracker.exception.ResourceNotFoundException;
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

    public Product addProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setCaloriesPer100g(request.caloriesPer100g());
        product.setProtein(request.protein());
        product.setCarbs(request.carbs());
        product.setFat(request.fat());
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    public Product updateProduct(Long id, ProductRequest request) {
        Product existing = getProductById(id);
        existing.setName(request.name());
        existing.setCaloriesPer100g(request.caloriesPer100g());
        existing.setProtein(request.protein());
        existing.setCarbs(request.carbs());
        existing.setFat(request.fat());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", id);
        }
        productRepository.deleteById(id);
    }
}
