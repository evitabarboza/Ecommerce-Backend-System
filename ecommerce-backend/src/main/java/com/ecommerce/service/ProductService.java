package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto dto);
    ProductDto updateProduct(Long id, ProductDto dto);
    void deleteProduct(Long id);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();
    Page<ProductDto> getProductsPaginated(int page, int size);
    List<ProductDto> getProductsByCategory(String category);
    List<ProductDto> getProductsByPriceRange(Double minPrice, Double maxPrice);
}
