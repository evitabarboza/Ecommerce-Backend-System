package com.ecommerce.service.impl;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Product product = mapToEntity(dto);
        Product saved = productRepository.save(product);
        return mapToDto(saved);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty()) throw new RuntimeException("Product not found");

        Product product = opt.get();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        product.setRating(dto.getRating());

        Product updated = productRepository.save(product);
        return mapToDto(updated);
    }

    
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDto> getProductsPaginated(int page, int size) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));
        List<ProductDto> dtoList = productPage.getContent()
                .stream().map(this::mapToDto).collect(Collectors.toList());
        return new PageImpl<>(dtoList, productPage.getPageable(), productPage.getTotalElements());
    }

 
    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository.findByCategory(category, PageRequest.of(0, 100))
                .stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ProductDto> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice, PageRequest.of(0, 100))
                .stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

 
    
    private ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategory(product.getCategory());
        dto.setImageUrl(product.getImageUrl());
        dto.setRating(product.getRating());
        return dto;
    }

    private Product mapToEntity(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        product.setRating(dto.getRating());
        return product;
    }
}
