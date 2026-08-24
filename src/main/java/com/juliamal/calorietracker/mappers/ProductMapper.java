package com.juliamal.calorietracker.mappers;

import com.juliamal.calorietracker.dto.response.ProductResponse;
import com.juliamal.calorietracker.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toDto(Product product);
    List<ProductResponse> toDtoList(List<Product> products);
}
