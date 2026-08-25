package com.juliamal.calorietracker.service;

import com.juliamal.calorietracker.dto.request.ProductRequest;
import com.juliamal.calorietracker.exception.ResourceNotFoundException;
import com.juliamal.calorietracker.model.Product;
import com.juliamal.calorietracker.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("Banana");
        sampleProduct.setCaloriesPer100g(89.0);
        sampleProduct.setProtein(1.1);
        sampleProduct.setCarbs(23.0);
        sampleProduct.setFat(0.3);
    }

    @Test
    void getProductById_whenProductExists_returnsProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        Product result = productService.getProductById(1L);
        assertThat(result.getName()).isEqualTo("Banana");
        assertThat(result.getCaloriesPer100g()).isEqualTo(89.0);
    }

    @Test
    void getProductById_whenProductDoesNotExist_throwsResourceNotFoundException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void addProduct_savesAndReturnsProduct() {
        ProductRequest request = new ProductRequest("Apple", 52.0, 0.3, 14.0, 0.2);
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);
        Product result = productService.addProduct(request);
        assertThat(result).isEqualTo(sampleProduct);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteProduct_whenProductExists_deletesIt() {
        when(productRepository.existsById(1L)).thenReturn(true);
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProduct_whenProductDoesNotExist_throwsAndNeverCallsDelete() {
        when(productRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> productService.deleteProduct(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(productRepository, never()).deleteById(any());
    }

    @Test
    void getAllProducts_returnsListFromRepository() {
        when(productRepository.findAll()).thenReturn(List.of(sampleProduct));
        List<Product> result = productService.getAllProducts();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Banana");
    }
}
