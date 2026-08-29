package com.juliamal.calorietracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juliamal.calorietracker.dto.request.ProductRequest;
import com.juliamal.calorietracker.dto.response.ProductResponse;
import com.juliamal.calorietracker.exception.ResourceNotFoundException;
import com.juliamal.calorietracker.mappers.ProductMapper;
import com.juliamal.calorietracker.model.Product;
import com.juliamal.calorietracker.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ProductMapper productMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getProductById_whenExists_returns200WithProduct() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Banana");

        ProductResponse response = new ProductResponse();
        response.setId(1L);
        response.setName("Banana");
        response.setCaloriesPer100g(89.0);

        when(productService.getProductById(1L)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(response);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Banana"))
                .andExpect(jsonPath("$.caloriesPer100g").value(89.0));
    }

    @Test
    void getProductById_whenNotFound_returns404WithErrorBody() throws Exception {
        when(productService.getProductById(99L))
                .thenThrow(new ResourceNotFoundException("Product", 99L));

        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found with id: 99"));
    }

    @Test
    void addProduct_withValidBody_returns200WithSavedProduct() throws Exception {
        ProductRequest request = new ProductRequest("Apple", 52.0, 0.3, 14.0, 0.2);

        Product saved = new Product();
        saved.setId(5L);
        saved.setName("Apple");

        ProductResponse response = new ProductResponse();
        response.setId(5L);
        response.setName("Apple");

        when(productService.addProduct(any(ProductRequest.class))).thenReturn(saved);
        when(productMapper.toDto(saved)).thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value("Apple"));
    }

    @Test
    void addProduct_withBlankName_returns400ValidationError() throws Exception {
        ProductRequest invalidRequest = new ProductRequest("", 52.0, 0.3, 14.0, 0.2);

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.name").value("Product name cannot be blank"));

        verify(productService, never()).addProduct(any());
    }

    @Test
    void addProduct_withNegativeCalories_returns400ValidationError() throws Exception {
        ProductRequest invalidRequest = new ProductRequest("Cake", -100.0, 0.3, 14.0, 0.2);

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.caloriesPer100g").value("Calories cannot be negative"));
    }

    @Test
    void deleteProduct_whenExists_returns204() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    void deleteProduct_whenNotFound_returns404() throws Exception {
        doThrow(new ResourceNotFoundException("Product", 99L))
                .when(productService).deleteProduct(99L);

        mockMvc.perform(delete("/api/products/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllProducts_returns200WithList() throws Exception {
        Product product = new Product();
        product.setId(1L);
        ProductResponse response = new ProductResponse();
        response.setId(1L);
        response.setName("Banana");

        when(productService.getAllProducts()).thenReturn(List.of(product));
        when(productMapper.toDtoList(List.of(product))).thenReturn(List.of(response));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Banana"));
    }
}
