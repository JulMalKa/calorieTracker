package com.juliamal.calorietracker.controller;

import com.juliamal.calorietracker.dto.request.ProductRequest;
import com.juliamal.calorietracker.dto.response.ProductResponse;
import com.juliamal.calorietracker.mappers.ProductMapper;
import com.juliamal.calorietracker.model.Product;
import com.juliamal.calorietracker.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        var products = productService.getAllProducts();
        return ResponseEntity.ok(productMapper.toDtoList(products));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody ProductRequest request) {
        var savedProduct = productService.addProduct(request);
        return ResponseEntity.ok(productMapper.toDto(savedProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        var product = productService.getProductById(id);
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        var updated = productService.updateProduct(id, request);
        return ResponseEntity.ok(productMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
